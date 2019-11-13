package de.mpg.mpi_inf.ambiversenlu.kg.resource.impl;

import de.mpg.mpi_inf.ambiversenlu.kg.api.CategoriesApi;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.model.CategoriesResponse;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Category;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Meta;
import de.mpg.mpi_inf.ambiversenlu.kg.resource.impl.mocks.MockedIDaoFactory;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.assertEquals;

//import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Categories Unit test.
 */
public class CategoriesResourceImplMockedTest extends JerseyTest {

  private static String PATH = "categories";

  @Override public Application configure() {
    ResourceConfig config = new ResourceConfig(CategoriesApi.class);

    //Register the Mocked Factory here
    AbstractBinder binder = new AbstractBinder() {

      @Override protected void configure() {
        bindFactory(CategoriesResourceImplMockedTest.MockedDaoFactory.class).to(IDao.class).named("DaoNeo4j").in(RequestScoped.class);
      }
    };
    config.register(binder);

    // Find first available port.
    forceSet(TestProperties.CONTAINER_PORT, "0");
    return config;
  }

  private static class MockedDaoFactory extends MockedIDaoFactory {

    @Override protected Map<String, Category> getCategoryMetadataByKbId() {
      Map<String, Category> categories = new HashMap<>();

      Category category1 = new Category();
      category1.setId("YAGO3:<wikicat_Polydor_Records_artists>");
      category1.setName("Polydor Records artists");
      categories.put(category1.getId(), category1);

      Category category2 = new Category();
      category2.setId("YAGO3:<wikicat_British_Invasion_artists>");
      category2.setName("British Invasion artists");
      categories.put(category2.getId(), category2);

      return categories;
    }

    @Override protected Meta getMeta() {
      Meta meta = new Meta();

      List<String> languages = new ArrayList<>();
      languages.add("en");

      meta.setDumpVersion("20170620");
      meta.setLanguages(languages);
      meta.setCollectionSize(0);

      String str = "2017-10-25 14:30:48";
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
      meta.setCreationDate(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));

      return meta;
    }
  }



//  @Test public void testGetCategoriesByIds() throws Exception {
//
//    Set<String> categories = new HashSet<>();
//    categories.add(URLEncoder.encode("YAGO3:<wikicat_FC_Bayern_Munich_players>", "UTF-8"));
//    categories.add(URLEncoder.encode("YAGO3:<wikicat_Germany_international_footballers>", "UTF-8"));
//    String path = StringUtils.join(categories, ",");
//
//    // Fire GET request and get the response
//    Response response = target(PATH).path(path).queryParam("offset", 0).queryParam("limit", 2).request(MediaType.APPLICATION_JSON_TYPE).buildGet()
//        .invoke();
//
//    //Check the status of the response
//    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//
//    //Read the response entity
//    CategoriesResponse metadata = response.readEntity(CategoriesResponse.class);
//
//    //Assert whether the input and the output sizes match
//    assertEquals(categories.size(), metadata.getCategories().size());
//
//    assertCategoryMetadata(metadata);
//
//    response.close();
//  }

  @Test public void testPostCategories() throws Exception {
    List<String> categories = new ArrayList<>();
    categories.add("YAGO3:<wikicat_FC_Bayern_Munich_players>");
    categories.add("YAGO3:<wikicat_Germany_international_footballers>");

    // Fire GET request and get the response
    Response response = target(PATH).queryParam("offset", 0).queryParam("limit", 100).request(MediaType.APPLICATION_JSON_TYPE)
        .buildPost(javax.ws.rs.client.Entity.entity(categories, MediaType.APPLICATION_JSON_TYPE)).invoke();

    //Check the status of the response
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    //Read the response entity
    CategoriesResponse metadata = response.readEntity(CategoriesResponse.class);

    //Assert whether the input and the output sizes match
    assertEquals(categories.size(), metadata.getCategories().size());

    assertCategoryMetadata(metadata);

    response.close();
  }

  @Test public void testGetCategoriesMeta() {
    Response response = target(PATH).path("_meta").request(MediaType.APPLICATION_JSON_TYPE).get();

    //Check the status of the response
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    Meta meta = response.readEntity(Meta.class);
    //assertCategoryMeta(meta);
    response.close();
  }

  /**
   * Common function to test if actual response matches expected response
   *
   * @param metadata
   */
  private static void assertCategoryMetadata(CategoriesResponse metadata) {
    MockedDaoFactory daoFactory = new MockedDaoFactory();
    Map<String, Category> expectedCategories = daoFactory.getCategoryMetadataByKbId();

    // Check whether the size of the entities are the same
    assertEquals(metadata.getCategories().size(), expectedCategories.size());

    //Assert the some constant entity values
    Map<String, Category> categories = metadata.getCategories();
    assertEquals(expectedCategories, categories);
//    for (int i = 0; i < expectedCategories.size(); i++) {
//      assertEquals(categories.get(i).getId(), categories.get(i).getId());
//      assertEquals(categories.get(i).getName(), categories.get(i).getName());
//    }
  }

//  private static void assertCategoryMeta(Meta meta) {
//    MockedDaoFactory daoFactory = new MockedDaoFactory();
//    Meta entityMeta = daoFactory.getMeta();
//
//    assertEquals(entityMeta.getCollectionSize(), meta.getCollectionSize());
//    assertEquals(entityMeta.getCreationDate(), meta.getCreationDate());
//    assertEquals(entityMeta.getDumpVersion(), meta.getDumpVersion());
//    assertEquals(entityMeta.getLanguages(), meta.getLanguages());
//
//  }
}
