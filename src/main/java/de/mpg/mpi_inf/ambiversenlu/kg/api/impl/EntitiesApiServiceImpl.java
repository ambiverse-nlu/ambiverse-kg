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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-10-24T12:54:00.720Z[GMT]")
public class EntitiesApiServiceImpl extends EntitiesApiService {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EntitiesApiServiceImpl.class);

    public static final String ID_PREFIX = "http://www.wikidata.org/entity/";


    //NK2 injection doesn't work. For now use this
    public EntitiesApiServiceImpl() {

    }

    @Override
    public Response entitiesGet( @NotNull String ids, String authorization,  Integer offset,  Integer limit, SecurityContext securityContext) throws NotFoundException {

        LOGGER.info("Input IDs: "+ids+" offset: "+offset+" limit: "+limit);
        EntitiesResponse response = null;
        if(ids == null) {
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "The imput cannot be empty")).build();
        }
        List<String> idList = Arrays.asList(ids.split(","));
        for (int i = 0; i < idList.size(); i++) {
            if (!idList.get(i).startsWith("http://")) {
                idList.set(i, ID_PREFIX + idList.get(i));
            }
        }
        LOGGER.info("Getting entity metadata for entity IDs: " + idList + "size: " + idList.size());

        try {
            response = getEntityMetadata(idList, offset, limit);
            return Response.ok().entity(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage())).build();
        }
    }
    @Override
    public Response entitiesIdGet(String id, String authorization, SecurityContext securityContext) throws NotFoundException {
        if(id  == null) {
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR,"The input cannot be empty")).build();
        }
        if(!id.startsWith("http://")) {
            id = ID_PREFIX+id;
        }

        List<String> idList = new ArrayList<>();
        idList.add(id);

        EntitiesResponse response = null;
        try {
            response = getEntityMetadata(idList, 0, 1);
            return Response.ok().entity(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage())).build();
        }

    }
    @Override
    public Response entitiesMetaGet(String authorization, SecurityContext securityContext) throws NotFoundException {

        LOGGER.info("Getting meta information for /entities resource");

        try {
            return Response.ok().entity(getDataAccess().getEntitiesMeta()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage())).build();
        }

    }
    @Override
    public Response entitiesPost(List<String> body, String authorization,  Integer offset,  Integer limit, SecurityContext securityContext) throws NotFoundException {
        EntitiesResponse response = null;
        if(body == null) {
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "The input cannot be empty")).build();
        }
        for (int i = 0; i < body.size(); i++) {
            if (!body.get(i).startsWith("http://")) {
                body.set(i, ID_PREFIX + body.get(i));
            }
        }

        try {
            response = getEntityMetadata(body, offset, limit);
            return Response.ok().entity(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage())).build();
        }
    }

    @Override
    public Response entitiesSearchByCoordinatesGet( @NotNull BigDecimal latitude,  @NotNull BigDecimal longitude, String authorization,  BigDecimal distance, SecurityContext securityContext) throws NotFoundException {
        EntitiesSearchRequest body = new EntitiesSearchRequest();
        body.setLatitude(latitude);
        body.setLongitude(longitude);
        body.setDistance(distance);

        LOGGER.info("EntitiesSearchRequest: " + body);

        try {
            return Response.ok().entity(getDataAccess().searchEntities(body)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage())).build();
        }
    }
    @Override
    public Response entitiesSearchByCoordinatesPost(EntitiesSearchRequest body, String authorization, SecurityContext securityContext) throws NotFoundException {

        LOGGER.info("EntitiesSearchRequest: " + body);

        try {

            return Response.ok().entity(getDataAccess().searchEntities(body)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage())).build();
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
