package de.mpg.mpi_inf.ambiversenlu.kg.resource.impl;

import de.mpg.mpi_inf.ambiversenlu.kg.api.EntitiesApi;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.impl.DaoNeo4j;

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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class EntitiesResourceImplIntegrationNeo4jTest extends JerseyTest {

  private static String PATH = "entities";
  private static final String ID_PREFIX = "http://www.wikidata.org/entity/";
  @Override
  protected Application configure() {
    ResourceConfig config = new ResourceConfig(EntitiesApi.class);

    AbstractBinder binder = new AbstractBinder() {
      @Override
      protected void configure() {
        bind(DaoNeo4j.class).to(IDao.class).withMetadata("test", "test")
            .named("DaoNeo4j").in(RequestScoped.class);

      }
    };

    // Find first available port.
    forceSet(TestProperties.CONTAINER_PORT, "0");

    config.register(binder);
    return config;
  }

  @Test public void testPostEntities() throws Exception {
    List<String> entities = new ArrayList<>();
    entities.add("<" + ID_PREFIX+"Q64" + ">");
    entities.add("<" + ID_PREFIX+"Q1077636" + ">");

    // Fire GET request and get the response
    Response response = target(PATH)
        .queryParam("offset", 0)
        .queryParam("limit", 100)
        .request(MediaType.APPLICATION_JSON_TYPE)
        .buildPost(javax.ws.rs.client.Entity.entity(entities, MediaType.APPLICATION_JSON_TYPE))
        .invoke();

    //Check the status of the response
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    response.close();
  }

  @Test
  public void testGetEntitiesByIds() throws Exception {

    Set<String> idsSet = new HashSet<>();
    idsSet.add(URLEncoder.encode("<" + ID_PREFIX+"Q64" + ">", "UTF-8"));
    idsSet.add(URLEncoder.encode("<" + ID_PREFIX+"Q1077636" + ">", "UTF-8"));
    String path = StringUtils.join(idsSet, ",");

    // Fire GET request and get the response
    Response response = target(PATH)
        .path(path)
        .queryParam("offset", 0)
        .queryParam("limit", 2)
        .request(MediaType.APPLICATION_JSON_TYPE)
        .buildGet().invoke();

    //Check the status of the response
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    response.close();
  }


}