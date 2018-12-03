package de.mpg.mpi_inf.ambiversenlu.kg.dao.impl;

import de.mpg.mpi_inf.ambiversenlu.kg.config.Neo4jConfig;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;

import de.mpg.mpi_inf.ambiversenlu.kg.model.*;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLTimeoutException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public class DaoNeo4j implements IDao, AutoCloseable{

  private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DaoNeo4j.class);

  private final Pattern languageCodedValuePattern = Pattern.compile("<?(?<value0>http:\\/\\/(?<language0>en|de|es|zh|fr|ar|ru|cs)\\..*)>|<((?<language1>en|de|es|zh|fr|ar|ru|cs)\\/)?(?<value1>.*)>|(?<value2>.*)@(?<language2>en|de|es|zh|fr|ar|ru|cs)");

  private enum OrderedLanguageCode {
      EN,
      DE,
      ES,
      ZH,
      RU,
      CS,
  }
  
  private static Driver driver;
   
  public DaoNeo4j() {
    String dbUri = Neo4jConfig.get("neo4j.uri");
    String user = Neo4jConfig.get("neo4j.user");
    String password = Neo4jConfig.get("neo4j.password");

    boolean shouldWait = Neo4jConfig.getBoolean("startup.wait");
    int maxWaitTimeInSeconds = Neo4jConfig.getAsInt("startup.timeoutins");
    int totalWaitTimeInSeconds = 0;
    LOGGER.info("Creating connection");
    do {
      try {
        LOGGER.info("Connecting to Neo4j at {}.", dbUri);
        driver = GraphDatabase.driver(dbUri, AuthTokens.basic(user, password));
      } catch(ServiceUnavailableException e) {
        try {
          LOGGER.info("Neo4j DB seems unavailable. This is expected during first startup using Docker. " +
              "Waiting for 60s in addition (already waited {}s, will wait up to {}s in total).", totalWaitTimeInSeconds, maxWaitTimeInSeconds);
          Thread.sleep(60000);
          totalWaitTimeInSeconds += 60;

          if (totalWaitTimeInSeconds >= maxWaitTimeInSeconds) {
            throw new ServiceUnavailableException("Neo4j DB still seems unavailable after waiting " + totalWaitTimeInSeconds + "s. Giving up...");
          }
        } catch (InterruptedException ie) {
          throw new RuntimeException(ie);
        }
      }
    } while (driver == null && shouldWait && totalWaitTimeInSeconds < maxWaitTimeInSeconds);
  }

  @Override
  public Map<String, Entity> getEntityMetadataByKbId(List<String> ids) {
    LOGGER.debug("Retrieving metadata for "+ids);
    Session session = driver.session();
    
    String query = "MATCH (wd:WikidataInstance) \n"
        + "WHERE wd.url in $ids \n"
        + "with wd \n"
        + "OPTIONAL MATCH (e:Entity)-[:sameAs]-(wd) \n"
        + "with wd, collect(e.entity) as names \n"
        + "OPTIONAL MATCH (wd)-[:hasWikipediaUrl]->(wp:WikipediaUrl) \n"
        + "with wd, names, collect(wp.url) as links \n"
        + "OPTIONAL MATCH (wd)-[:hasImage]->(i:WikidataImage) \n"
        + "with wd, names, links, i \n"
        + "OPTIONAL MATCH (i)-[:hasLicense]->(l:WikidataImageLicense) \n"
        + "with wd, names, links, i, collect(l) as licenses \n"
        + "OPTIONAL MATCH (i)-[:hasAuthor]->(au:Author)\n"
        + "with wd, names, links, licenses,  i.imageUrl as imageUrl, au as author \n"
        + "OPTIONAL MATCH (wd)-[:hasType]->(t:Type) \n"
        + "with wd, names, links, imageUrl, licenses, author, collect(t.type) as categories \n"
        + "OPTIONAL MATCH (wd)-[:hasGeoLocation]->(g:Location) \n"
        + "with wd, names, links, imageUrl, licenses, author, categories, g as geo \n"
        + "RETURN \n"
        + "wd.url as id, \n"
        + "names, \n"
        + "collect(wd.shortDescriptions) as shortDescriptions, \n"
        + "collect(wd.longDescriptions) as longDescriptions, \n"
        + "links, \n"
        + "imageUrl, \n"
        + "licenses, \n"
        + "author, \n"
        + "categories, \n"
        + "geo";


    long startTime = System.currentTimeMillis();
    StatementResult qResult = session.run( query,
                                           parameters( "ids", ids.stream()
                                                                 .map(id -> "<" + id + ">")
                                                                     .toArray()) );


    Map<String, Entity> result = new LinkedHashMap<>();

    //Populate the keys to preserve the order
    for(String entityId : ids) {
      result.putIfAbsent(entityId, new Entity());
    }
    
    while (qResult.hasNext()) {
      Record record = qResult.next();
      
      Entity e = new Entity();
      
      // ID
      e.setId(removeAngleBrackets(record.get("id").asString()));

      // Names
      if (!record.get("names").isNull() && !record.get("names").isEmpty()) {
        Map<String, Label> names = getValuesByLangauge(record.get("names").asList(org.neo4j.driver.v1.Values.ofString()), true);
        e.setNames(names);
      }

      // Descriptions
      if (!record.get("shortDescriptions").isNull() && !record.get("shortDescriptions").isEmpty()) {
        Map<String, Label> descriptions = getValuesByLangauge(record.get("shortDescriptions").get(0).asList(Values.ofString()), false);
        e.setDescriptions(descriptions);
      }

      // Long descriptions
      if(!record.get("longDescriptions").isNull() && !record.get("longDescriptions").isEmpty()) {
        Map<String, Label> detailDescriptions = getValuesByLangauge(record.get("longDescriptions").get(0).asList(Values.ofString()), false);
        e.setDetailedDescriptions(detailDescriptions);
      }

      // Links
      if (!record.get("links").isNull() && !record.get("links").isEmpty()) {
        Map<String, Label> links = getValuesByLangauge(record.get("links").asList(Values.ofString()), false);
        e.setLinks(links);
      }


      // Image
      if(!record.get("imageUrl").isNull() && !record.get("imageUrl").isEmpty()) {
        Image image = new Image();

        image.setUrl(removeAngleBrackets(record.get("imageUrl").asString()));
        e.setImage(image);

        List<License> licenses = new ArrayList<>();
        if(!record.get("licenses").isNull() && !record.get("licenses").isEmpty()) {

          if(!record.get("licenses").asList(Values.ofValue()).isEmpty()) {
            for(Value v : record.get("licenses").asList(Values.ofValue())) {
              License license = new License();
              license.setName(removeAngleBrackets(v.asMap().get("name").toString()));
              license.setUrl(removeAngleBrackets(v.asMap().get("url").toString()));
              licenses.add(license);
            }
          }

        }
        image.setLicenses(licenses);

        if(!record.get("author").isNull() ) {
          Author author = new Author();

          author.setName(removeAngleBrackets(record.get("author").asMap().get("name").toString()).replaceAll("_", " "));
          author.setUrl(removeAngleBrackets(record.get("author").asMap().get("url").toString()));
          image.setAuthor(author);
        }
      }

      if(!record.get("geo").isNull() ) {
        GeoLocation location = new GeoLocation();

        location.setLatitude(new BigDecimal(record.get("geo").asMap().get("latitude").toString()));
        location.setLongitude(new BigDecimal(record.get("geo").asMap().get("longitude").toString()));
        e.setGeoLocation(location);
      }

      // Categories
      if (!record.get("categories").isNull() && !record.get("categories").isEmpty()) {
        Set<String> allTypes = new HashSet<>();
        List<String> categoriesList = record.get("categories").asList(Values.ofString());
        for(String category : categoriesList) {
          allTypes.add("YAGO3:"+category);
        }

        //Set the most salient type
        e.setType(getEntityType(allTypes));

        e.setCategories(new HashSet<>(allTypes));
      }
      result.put(e.getId(), e);
    }
    session.close();
    driver.close();
    LOGGER.debug("Retrieval finished in {}ms", (System.currentTimeMillis()-startTime));
    return result;
  }

  @Override
  public List<String> searchEntities(EntitiesSearchRequest request) {
    LOGGER.info("Searching entities: " + request);
    Session session = driver.session();

    //Old spatial index
//    String query = "CALL spatial.withinDistance(\"geom\", {latitude:$lat, longitude:$lon}, $distance) YIELD node AS l "
//        + "MATCH (wikidata:WikidataInstance)-[:hasGeoLocation]->(l) "
//        + "MATCH (wikidata)-[:sameAs]-(e:Entity) "
//        + "RETURN DISTINCT wikidata.url as url";

    //New point index.
    String query = "WITH point({latitude:$lat, longitude:$lon}) as here\n"
        + "MATCH (l:Location) WHERE distance(l.location, here) < ($distance * 1000)\n"
        + "with l\n"
        + "MATCH (wikidata:WikidataInstance)-[:hasGeoLocation]->(l)\n" + "RETURN DISTINCT wikidata.url as url";

    StatementResult qResult = session.run(query,
                                          parameters("lat", request.getLatitude().doubleValue(),
                                                     "lon", request.getLongitude().doubleValue(),
                                                     "distance", request.getDistance().doubleValue()) );
    List<String> entityIDs = new ArrayList<>();
    
    while (qResult.hasNext()) {
      Record record = qResult.next();     
      
      String url = record.get("url").toString();
      
      // Clean Wikidata url from quotes and angle brackets.
      url = url.replaceAll("^\"<", "");
      url = url.replaceAll(">\"$", "");
      url = url.replaceAll("^\"", "");
      url = url.replaceAll("\"$", "");
      
      entityIDs.add(url);
    }
    session.close();
    return entityIDs;
  }

  @Override
  public Map<String, Category> getCategoryMetadataByKbId(List<String> kbIds){
    LOGGER.debug("Retrieving metadata for "+kbIds);
    
    List<String> categoryNamesInDB = new ArrayList<>();
    Map<String, Category> result = new LinkedHashMap<>();

    for(String id : kbIds) {
      result.putIfAbsent(id, new Category());
    }
    
    kbIds.stream().forEach(k-> {
      Category category = new Category();
      category.setId(k);
      String name = k.substring(k.indexOf(":") + 1);

      name = name.replaceAll("[<>:]", "");
      name = name.replaceAll("YAGO3", "");
      categoryNamesInDB.add("<" + name + ">");
      if(name.contains("wordnet")) {
        name = name.replaceAll("[0-9]", "");
      }

      name = name.replaceAll("wikicat", "");
      name = name.replaceAll("wordnet", "");
      name = name.replaceAll("_", " ");
      category.setName(name.trim());

      result.put(k, category);
    });
    
    Session session = driver.session();
    
    String query = "MATCH (t:Type)"
        + " WHERE t.type in $ids"
        + " RETURN t.type as type,"
        + " t.gloss as gloss";
    
    StatementResult qResult = session.run( query,
        parameters( "ids", categoryNamesInDB.toArray()) );
    
    Map<String, String> glosses = new HashMap<>();
    while ( qResult.hasNext() ) {
      Record record = qResult.next();
      
      String type = record.get("type").toString().replaceAll("\"", "");
      String gloss = record.get("gloss").toString();
      
      if (gloss != null && gloss != "NULL") {
        glosses.put(type, gloss);
      }
    }
    session.close();

    for (Map.Entry<String, Category> entry : result.entrySet()) {
      String typeName = entry.getValue().getId().replaceAll("YAGO3:", "");
      if (glosses.containsKey(typeName)) {
        //c.setAdditionalProperty("gloss", glosses.get(typeName));
        //TODO: Use getValuesByLangauge to get the glosses by language code from the list of glossses and add them to the descriptions map
        //System.out.println(typeName+" --- "+glosses.get(typeName));
      }
    }
    
    return result;
  }

  @Override
  public Meta getEntitiesMeta() throws Exception {
    Session session = driver.session();
    
    String query = "MATCH (m:Meta)"
        + " RETURN m.confName AS confName,"
        + " m.KB_creationDate AS KB_creationDate,"
        + " m.KB_WikipediaSources AS KB_WikipediaSources,"
        + " m.languages AS languages,"
        + " m.collection_size AS collection_size,"
        + " m.datasource_YAGO3 AS datasource_YAGO3,"
        + " m.creationDate AS creationDate";
    
    StatementResult qResult = session.run(query);
    Meta meta = new Meta();
    Record record = qResult.single();
    if (record != null) {
      List<Object> wikipediaSources = record.get("KB_WikipediaSources").asList();
      if (wikipediaSources != null) {
        String dumpVersion = "";
        for (Object wikiSource : wikipediaSources) {
          String currentVersion = wikiSource.toString().replaceAll("\\D+", "");
          if(currentVersion.compareTo(dumpVersion) >=1 ) {
            dumpVersion = currentVersion;
          }
        }
        meta.setDumpVersion(dumpVersion);
      }
    
      String creationDate = record.get("creationDate").asString();
      if (creationDate != null) {
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("E MMM d HH:mm:ss z yyyy");
        LocalDateTime dateTime = LocalDateTime.parse(creationDate, formatter);
        Date dumpDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        meta.setCreationDate(dumpDate);
      }
      
      Integer collectionSize = new Integer(record.get("collection_size").asString());
      if (collectionSize != null) {
        meta.setCollectionSize(collectionSize);
      }
      
      List<Object> languagesTmp = record.get("languages").asList();
      if (languagesTmp != null) {
        List<String> languages = new ArrayList<>();
        for (Object l:languagesTmp) {
          languages.add(l.toString());
        }
        meta.setLanguages(languages);
      }
    }
    
    return meta;
  }

  @Override
  public Meta getCategoriesMeta() throws Exception {
    return getEntitiesMeta();
  }

  @Override
  public void close() throws Exception {
    driver.close();
  }
  
  private String removeAngleBrackets(String s) {
    if (s.startsWith("<") && s.endsWith(">")) {
      return s.substring(1, s.length() - 1);
    }
    
    return s;
  }
  
  private <T> T firstNonNull(T... objects) {
    for (T obj : objects) {
      if (obj != null) {
        return obj;
      }
    }
    
    return null;
  }

  private Map<String, Label> getValuesByLangauge(List<String> values, boolean replaceUnderscore) {
    Map<String, Label> valuesByLanguage = new LinkedHashMap<>();

    //TODO: A hack to order the hashMap
    valuesByLanguage.putIfAbsent("en", null);
    valuesByLanguage.putIfAbsent("de", null);
    valuesByLanguage.putIfAbsent("es", null);
    valuesByLanguage.putIfAbsent("zh", null);
    valuesByLanguage.putIfAbsent("ru", null);

    for (String value : values) {
      Matcher m = languageCodedValuePattern.matcher(value);
      if (m.matches()) {
        String matchLanguage = firstNonNull(m.group("language0"), m.group("language1"), m.group("language2"));
        String matchValue = firstNonNull(m.group("value0"), m.group("value1"), m.group("value2"));

        if (matchLanguage == null) {
          matchLanguage = "en";
        }

        if (!matchLanguage.equals("fr") && !matchLanguage.equals("ar")) {
          if (replaceUnderscore) {
            matchValue = matchValue.replaceAll("_", " ");
          }
          //valuesByLanguage.putIfAbsent(matchLanguage, new ArrayList<>());
          Label label = new Label();
          label.setLanguage(matchLanguage);
          label.setValue(matchValue);
          valuesByLanguage.put(matchLanguage, label);
        }
      }
    }

    //Remove the languages without value.
    for(Iterator<Map.Entry<String, Label>> it = valuesByLanguage.entrySet().iterator(); it.hasNext(); ) {
      Map.Entry<String, Label> entry = it.next();
      if(entry.getValue() == null) {
        it.remove();
      }
    }
    return valuesByLanguage;
  }
  
  private String getMostEnglish(List<String> values) {
    Map<Integer, String> orderedValues = new TreeMap<>(); 
    
    for (String value : values) {
      Matcher m = languageCodedValuePattern.matcher(value);
      
      if (m.matches()) {
        String matchLanguage = firstNonNull(m.group("language0"), m.group("language1"), m.group("language2"));
        String matchValue = firstNonNull(m.group("value0"), m.group("value1"), m.group("value2"));
        orderedValues.put(OrderedLanguageCode.valueOf(matchLanguage.toUpperCase()).ordinal(), matchValue);
      }
    }

    System.out.println(orderedValues);
    
    return orderedValues.size() > 0 ? orderedValues.entrySet().iterator().next().getValue() : null;
  }
  
  private boolean isEnglishCategory(String category) {
    // Crude heuristic: If there's a language code present, it's not English.  
    Matcher m = languageCodedValuePattern.matcher(category);
    
    return !m.matches();
  }

  public static Entity.TypeEnum getEntityType(Set<String> types) {

    if(types == null || types.isEmpty()) {
      return Entity.TypeEnum.UNKNOWN;
    }

    if (types.stream().anyMatch(str -> str.contains("person"))) {
      return Entity.TypeEnum.PERSON;
    }
    if (types.stream().anyMatch(str -> str.contains("yagoGeoEntity"))) {
      return Entity.TypeEnum.LOCATION;
    }
    if (types.stream().anyMatch(str -> str.contains("organization"))) {
      return Entity.TypeEnum.ORGANIZATION;
    }
    if (types.stream().anyMatch(str -> str.contains("artifact"))) {
      return Entity.TypeEnum.ARTIFACT;
    }
    if (types.stream().anyMatch(str -> str.contains("event"))) {
      return Entity.TypeEnum.EVENT;
    }
    return Entity.TypeEnum.OTHER;
  }


}
