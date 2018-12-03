package de.mpg.mpi_inf.ambiversenlu.kg.api.impl;

import de.mpg.mpi_inf.ambiversenlu.kg.api.ApiResponseMessage;
import de.mpg.mpi_inf.ambiversenlu.kg.api.EntitiesApiService;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import de.mpg.mpi_inf.ambiversenlu.kg.api.NotFoundException;

import de.mpg.mpi_inf.ambiversenlu.kg.model.EntitiesResponse;
import de.mpg.mpi_inf.ambiversenlu.kg.model.EntitiesSearchRequest;
import de.mpg.mpi_inf.ambiversenlu.kg.model.EntityRequest;
import de.mpg.mpi_inf.ambiversenlu.kg.model.MessageResponse;
import org.slf4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-02-21T12:33:39.350Z")
public class EntitiesApiServiceImpl extends EntitiesApiService {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EntitiesApiServiceImpl.class);

    public static final String ID_PREFIX = "http://www.wikidata.org/entity/";


    //NK2 injection doesn't work. For now use this
    public EntitiesApiServiceImpl() {

    }

    @Override
    public Response entitiesGet(String authorization,  @NotNull String ids,  Integer offset,  Integer limit, SecurityContext securityContext) throws NotFoundException {
        // do some magic!

        LOGGER.info("Input IDs: "+ids+" offset: "+offset+" limit: "+limit);
        List<String> idList = Arrays.asList(ids.split(","));
        for(int i=0; i<idList.size(); i++) {
          if(!idList.get(i).startsWith("http://")) {
            idList.set(i, ID_PREFIX+idList.get(i));
          }
        }
        LOGGER.info("Getting entity metadata for entity IDs: " + idList +"size: "+idList.size());

        EntitiesResponse response = null;
        try {
            response = getEntityMetadata(idList, offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new MessageResponse(ApiResponseMessage.ERROR, e.getMessage())).build();
        }

        return Response.ok().entity(response).build();
    }
    @Override
    public Response entitiesIdGet(String authorization, String id, SecurityContext securityContext) throws NotFoundException {

        if(!id.startsWith("http://")) {
            id = ID_PREFIX+id;
        }

        List<String> idList = new ArrayList<>();
        idList.add(id);

        EntitiesResponse response = null;
        try {
            response = getEntityMetadata(idList, 0, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new MessageResponse(ApiResponseMessage.ERROR, e.getMessage())).build();
        }
        // do some magic!
        return Response.ok().entity(response).build();
    }
    @Override
    public Response entitiesMetaGet(String authorization, SecurityContext securityContext) throws NotFoundException {

      LOGGER.info("Getting meta information for /entities resource");

      try {
        return Response.ok().entity(getDataAccess().getEntitiesMeta()).build();
      } catch (Exception e) {
        e.printStackTrace();
        return Response.serverError().entity(new MessageResponse(ApiResponseMessage.ERROR, e.getMessage())).build();
      }

    }
    @Override
    public Response entitiesPost(String authorization, EntityRequest body,  Integer offset,  Integer limit, SecurityContext securityContext) throws NotFoundException {
        EntitiesResponse response = null;
        for(int i=0; i<body.size(); i++) {
          if(!body.get(i).startsWith("http://")) {
            body.set(i, ID_PREFIX+body.get(i));
          }
        }

        try {
            response = getEntityMetadata(body, offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new MessageResponse(ApiResponseMessage.ERROR, e.getMessage())).build();
        }

        return Response.ok().entity(response).build();
    }
    @Override
    public Response entitiesSearchByCoordinatesGet(String authorization,  @NotNull BigDecimal latitude,  @NotNull BigDecimal longitude,  BigDecimal distance, SecurityContext securityContext) throws NotFoundException {
      EntitiesSearchRequest body = new EntitiesSearchRequest();
      body.setLatitude(latitude);
      body.setLongitude(longitude);
      body.setDistance(distance);

      LOGGER.info("EntitiesSearchRequest: " + body);

      try {
        return Response.ok().entity(getDataAccess().searchEntities(body)).build();
      } catch (Exception e) {
        e.printStackTrace();
        return Response.serverError().entity(new MessageResponse(ApiResponseMessage.ERROR, e.getMessage())).build();
      }
    }
    @Override
    public Response entitiesSearchByCoordinatesPost(String authorization, EntitiesSearchRequest body, SecurityContext securityContext) throws NotFoundException {

      LOGGER.info("EntitiesSearchRequest: " + body);

      try {

        return Response.ok().entity(getDataAccess().searchEntities(body)).build();
      } catch (Exception e) {
        e.printStackTrace();
        return Response.serverError().entity(new MessageResponse(ApiResponseMessage.ERROR, e.getMessage())).build();
      }

    }

    private EntitiesResponse getEntityMetadata(List<String> entities, final int offset, final int limit) throws Exception {
        EntitiesResponse entitiesResponse = new EntitiesResponse();

        long startTime = System.currentTimeMillis();

        entities = entities.subList(offset, Math.min(offset+limit, entities.size()));

        if (entities != null && !entities.isEmpty()) {
            LOGGER.debug("Using DAO Implementation "+getDataAccess().getClass().getCanonicalName());
            entitiesResponse.setEntities(getDataAccess().getEntityMetadataByKbId(entities));
        }
        Long responseTime = System.currentTimeMillis() - startTime;

        LOGGER.info("Getting entity metadata finished in "+responseTime+" ms");

        return entitiesResponse;
    }
}
