package de.mpg.mpi_inf.ambiversenlu.kg.resource.impl;

import de.mpg.mpi_inf.ambiversenlu.kg.api.CategoriesApi;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.impl.DaoNeo4j;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Categories Integration test
 */
public class CategoriesResourceImplIntegrationTest extends JerseyTest {

  private static String PATH = "categories";

  @Override protected Application configure() {
    ResourceConfig config = new ResourceConfig(CategoriesApi.class);

    AbstractBinder binder = new AbstractBinder() {
      @Override protected void configure() {
        bind(DaoNeo4j.class).to(IDao.class).withMetadata("test", "test").named("DaoNeo4j").in(RequestScoped.class);
      }
    };

    // Find first available port.
    forceSet(TestProperties.CONTAINER_PORT, "0");
    config.register(binder);
    return config;
  }

  @Test public void testPostCategories() throws Exception {
    List<String> categories = new ArrayList<>();
    categories.add("YAGO3:<wikicat_FC_Bayern_Munich_players>");
    categories.add("YAGO3:<wikicat_Germany_international_footballers>");


    // Fire GET request and get the response
    Response response = target(PATH).queryParam("offset", 0).queryParam("limit", 100).request(MediaType.APPLICATION_JSON_TYPE)
        .buildPost(javax.ws.rs.client.Entity.entity(categories, MediaType.APPLICATION_JSON_TYPE)).invoke();

    //Check the status of the response
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    response.close();
  }

  @Test public void testGetCategoriesByIds() throws Exception {

//    Set<String> idsSet = new HashSet<>();
//    idsSet.add(URLEncoder.encode("YAGO3:<wikicat_FC_Bayern_Munich_players>", "UTF-8"));
//    idsSet.add(URLEncoder.encode("YAGO3:<wikicat_Germany_international_footballers>", "UTF-8"));
//    String path = StringUtils.join(idsSet, ",");
//
//    // Fire GET request and get the response
//    Response response = target(PATH).path(path).queryParam("offset", 0).queryParam("limit", 2).request(MediaType.APPLICATION_JSON_TYPE).buildGet()
//        .invoke();
//
//    //Check the status of the response
//    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//    response.close();
  }
}