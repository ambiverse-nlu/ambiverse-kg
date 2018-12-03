package de.mpg.mpi_inf.ambiversenlu.kg.resource.impl;

import de.mpg.mpi_inf.ambiversenlu.kg.api.EntitiesApi;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.model.EntitiesResponse;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Entity;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Label;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Meta;
import de.mpg.mpi_inf.ambiversenlu.kg.resource.impl.mocks.MockedIDaoFactory;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Entities Unit Test
 */
public class EntitiesResourceImplMockedTest extends JerseyTest {

  private static String PATH = "entities";
  private static final String ID_PREFIX = "http://www.wikidata.org/entity/";

  private static class MockedDaoFactory extends MockedIDaoFactory {

    @Override protected Map<String, Entity> getEntityMetadataByKbId() {
      Map<String, Entity> entities = new HashMap<>();

      //Categories
      Set<String> categories1 = new HashSet<>();
      categories1.add("YAGO3:<wordnet_physical_entity_100001930>");
      categories1.add("YAGO3:<wikicat_Atco_Records_artists>");
      categories1.add("YAGO3:<wikicat_British_Invasion_artists>");
      categories1.add("YAGO3:<wordnet_whole_100003553>");
      categories1.add("YAGO3:<wordnet_person_100007846>");
      categories1.add("YAGO3:<wordnet_artist_109812338>");
      categories1.add("YAGO3:<wikicat_Decca_Records_artists>");
      categories1.add("YAGO3:<wikicat_Brunswick_Records_artists>");
      categories1.add("YAGO3:<wordnet_living_thing_100004258>");
      categories1.add("YAGO3:<yagoLegalActorGeo>");
      categories1.add("YAGO3:<wikicat_Polydor_Records_artists>");
      categories1.add("YAGO3:<wordnet_causal_agent_100007347>");
      categories1.add("YAGO3:<wikicat_Warner_Bros._Records_artists>");
      categories1.add("YAGO3:owl:Thing");
      categories1.add("YAGO3:<wikicat_Geffen_Records_artists>");
      categories1.add("YAGO3:<wordnet_object_100002684>");
      categories1.add("YAGO3:<wordnet_creator_109614315>");
      categories1.add("YAGO3:<wordnet_organism_100004475>");
      categories1.add("YAGO3:<yagoLegalActor>");


      Entity entity1 = new Entity();
      entity1.setId(ID_PREFIX+"Q93346");

      Map<String, Label> names = new HashMap<>();
      Label name = new Label();
      name.setLanguage("en");
      name.setValue("The Who");
      names.putIfAbsent("en", name);
      entity1.setNames(names);

      //Links
      Map<String, Label> links1 = new HashMap<>();
      Label link1 = new Label();
      link1.setLanguage("en");
      link1.setValue("http://en.wikipedia.org/wiki/The%20Who");
      links1.put("en", link1);
      entity1.setLinks(links1);

      Map<String, Label> descriptions = new HashMap<>();
      Label enDescription = new Label();
      enDescription.setLanguage("en");
      enDescription.setValue( "The Who are an English rock band formed in 1964 by Roger Daltrey (lead vocals, guitar, harmonica), Pete Townshend (guitar, vocals, keyboards), John Entwistle (bass, brass, vocals) and Keith Moon (drums, vocals). They became known for energetic live performances which often included instrument destruction.");
      descriptions.putIfAbsent("en", enDescription);
      entity1.setDescriptions(descriptions);

      entity1.setCategories(categories1);
      entities.put(entity1.getId(), entity1);



      //Categories
      Set<String> categories2 = new HashSet<>();
      categories2.add("YAGO3:<wordnet_physical_entity_100001930>");
      categories2.add("YAGO3:<wordnet_product_104007894>");
      categories2.add("YAGO3:<wikicat_The_Who_albums>");
      categories2.add("YAGO3:<wikicat_Rock_operas>");
      categories2.add("YAGO3:<wikicat_1969_albums>");
      categories2.add("YAGO3:<wordnet_album_106591815>");
      categories2.add("YAGO3:<wordnet_instrumentality_103575240>");
      categories2.add("YAGO3:<wordnet_rock_opera_106592281>");
      categories2.add("YAGO3:owl:Thing");
      categories2.add("YAGO3:<wordnet_impression_106590210>");
      categories2.add("YAGO3:<wordnet_work_104599396>");
      categories2.add("YAGO3:<wikicat_Track_Records_albums>");
      categories2.add("YAGO3:<wikicat_MCA_Records_albums>");
      categories2.add("YAGO3:<wikicat_Albums_produced_by_Kit_Lambert>");
      categories2.add("YAGO3:<wordnet_whole_100003553>");
      categories2.add("YAGO3:<wordnet_publication_106589574>");
      categories2.add("YAGO3:<wikicat_Concept_albums>");
      categories2.add("YAGO3:<wordnet_artifact_100021939>");
      categories2.add("YAGO3:<wikicat_English-language_albums>");
      categories2.add("YAGO3:<wikicat_Universal_Deluxe_Editions>");
      categories2.add("YAGO3:<wordnet_medium_106254669>");
      categories2.add("YAGO3:<wikicat_Decca_Records_albums>");
      categories2.add("YAGO3:<wordnet_edition_106590446>");
      categories2.add("YAGO3:<wordnet_creation_103129123>");
      categories2.add("YAGO3:<wordnet_object_100002684>");
      categories2.add("YAGO3:<wikicat_Polydor_Records_albums>");
      categories2.add("YAGO3:<wordnet_concept_album_106592078>");


      Entity entity2 = new Entity();
      entity2.setId(ID_PREFIX+"Q372055");

      Map<String, Label> names2 = new HashMap<>();
      Label name2 = new Label();
      name2.setLanguage("en");
      name2.setValue("Tommy (album)");
      names2.putIfAbsent("en", name2);
      entity1.setNames(names2);

      entity2.setNames(names2);

      //Links
      Map<String, Label> links2 = new HashMap<>();
      Label link2 = new Label();
      link2.setLanguage("en");
      link2.setValue("http://en.wikipedia.org/wiki/Tommy%20%28album%29");
      links2.put("en", link2);

      entity2.setLinks(links2);

      Map<String, Label> descriptions2 = new HashMap<>();
      Label enDescription2 = new Label();
      enDescription2.setLanguage("en");
      enDescription2.setValue( "Tommy is the fourth album by English rock band The Who, released by Track Records and Polydor Records in the UK and Decca Records/MCA in the US. A double album telling a loose story about a \\\"deaf, dumb and blind boy\\\", Tommy was the first musical work to be billed overtly as a rock opera. Released in 1969, the album was mostly composed by Pete Townshend. In 1998, it was inducted into the Grammy Hall of Fame for \\\"historical, artistic and significant value\\");
      descriptions2.putIfAbsent("en", enDescription);
      entity2.setDescriptions(descriptions);

      entity2.setCategories(categories2);
      entities.put(entity2.getId(), entity2);

      entities.put("AIDA:--OOKBE--", new Entity());

      return entities;
    }

    @Override protected Meta getMeta() {
      Meta meta = new Meta();

      List<String> languages = new ArrayList<>();
      languages.add("en");

      meta.setDumpVersion("v1beta1");
      meta.setLanguages(languages);
      meta.setCollectionSize(560789);

      String str = "2016-05-31 15:20:50";
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
      meta.setCreationDate(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));

      return meta;
    }

  }

  @Override public Application configure() {
    ResourceConfig config = new ResourceConfig(EntitiesApi.class);

    //Register the Mocked Factory here
    AbstractBinder binder = new AbstractBinder() {

      @Override protected void configure() {
        bindFactory(EntitiesResourceImplMockedTest.MockedDaoFactory.class).to(IDao.class).named("DaoNeo4j").in(RequestScoped.class);
      }
    };
    config.register(binder);

    // Find first available port.
    forceSet(TestProperties.CONTAINER_PORT, "0");
    return config;
  }

  @Test public void testGetEntitiesByIds() throws UnsupportedEncodingException {
    Set<String> entities = new HashSet<>();
    entities.add(URLEncoder.encode(ID_PREFIX+"Q93346", "UTF-8"));
    entities.add(URLEncoder.encode(ID_PREFIX+"Q372055", "UTF-8"));
    entities.add(URLEncoder.encode("AIDA:--OOKBE--", "UTF-8"));
    String path = StringUtils.join(entities, ",");

    Response response = target(PATH).path(path).request(MediaType.APPLICATION_JSON_TYPE).get();

    //Check the status of the response
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    //Read the response entity
    EntitiesResponse metadata = response.readEntity(EntitiesResponse.class);

    //Assert whether the input and the output sizes match
    assertEquals(entities.size(), metadata.getEntities().size());

    assertEntityMetadata(metadata);

    response.close();
  }

  @Test public void testPostEntities() {
    List<String> entities = new ArrayList<>();
    entities.add(ID_PREFIX+"Q93346");
    entities.add(ID_PREFIX+"Q372055");
    entities.add("AIDA:--OOKBE--");

    // Fire GET request and get the response
    Response response = target(PATH).queryParam("offset", 0).queryParam("limit", 100).request(MediaType.APPLICATION_JSON_TYPE)
        .buildPost(javax.ws.rs.client.Entity.entity(entities, MediaType.APPLICATION_JSON_TYPE)).invoke();

    //Check the status of the response
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    //Read the response entity
    EntitiesResponse metadata = response.readEntity(EntitiesResponse.class);

    //Assert whether the input and the output sizes match
    assertEquals(entities.size(), metadata.getEntities().size());

    assertEntityMetadata(metadata);
    response.close();
  }

  @Test public void testGetEntitiesMeta() {
    Response response = target(PATH).path("_meta").request(MediaType.APPLICATION_JSON_TYPE).get();

    //Check the status of the response
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    Meta meta = response.readEntity(Meta.class);
    assertEntityMeta(meta);
    response.close();
  }

  /**
   * Common function to test if actual response matches expected response
   *
   * @param metadata
   */
  private static void assertEntityMetadata(EntitiesResponse metadata) {
    MockedDaoFactory daoFactory = new MockedDaoFactory();
    Map<String, Entity> expectedEntities = daoFactory.getEntityMetadataByKbId();

    // Check whether the size of the entities are the same
    assertEquals(metadata.getEntities().size(), expectedEntities.size());

    //Assert the some constant entity values
    Map<String, Entity> entities = metadata.getEntities();
    for (Map.Entry<String, Entity> entry : expectedEntities.entrySet()) {

      assertEquals(entities.get(entry.getKey()).getId(), entry.getValue().getId());
      assertEquals(entities.get(entry.getKey()).getNames(), entry.getValue().getNames());
      assertEquals(entities.get(entry.getKey()).getImage(), entry.getValue().getImage());
      assertEquals(entities.get(entry.getKey()).getDescriptions(), entry.getValue().getDescriptions());
      assertEquals(entities.get(entry.getKey()).getLinks(), entry.getValue().getLinks());

      assertEquals(entities.get(entry.getKey()).getCategories(), entry.getValue().getCategories());
    }
  }

  private static void assertEntityMeta(Meta meta) {
    MockedDaoFactory daoFactory = new MockedDaoFactory();
    Meta entityMeta = daoFactory.getMeta();

    assertEquals(entityMeta.getCollectionSize(), meta.getCollectionSize());
    assertEquals(entityMeta.getCreationDate(), meta.getCreationDate());
    assertEquals(entityMeta.getDumpVersion(), meta.getDumpVersion());
    assertEquals(entityMeta.getLanguages(), meta.getLanguages());

  }
}
