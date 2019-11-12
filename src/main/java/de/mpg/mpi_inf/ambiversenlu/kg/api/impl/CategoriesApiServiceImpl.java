package de.mpg.mpi_inf.ambiversenlu.kg.api.impl;

import de.mpg.mpi_inf.ambiversenlu.kg.api.ApiResponseMessage;
import de.mpg.mpi_inf.ambiversenlu.kg.api.CategoriesApiService;


import java.util.List;
import de.mpg.mpi_inf.ambiversenlu.kg.api.NotFoundException;

import de.mpg.mpi_inf.ambiversenlu.kg.model.CategoriesResponse;
import de.mpg.mpi_inf.ambiversenlu.kg.model.CategoryRequest;
import de.mpg.mpi_inf.ambiversenlu.kg.model.MessageResponse;
import org.slf4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-10-24T12:54:00.720Z[GMT]")
public class CategoriesApiServiceImpl extends CategoriesApiService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CategoriesApiServiceImpl.class);

    public static final String ID_PREFIX = "http://www.wikidata.org/entity/";


    public CategoriesApiServiceImpl() {
    }


    @Override
    public Response categoriesMetaGet(String authorization, SecurityContext securityContext) throws NotFoundException {
        LOGGER.info("Getting meta information for /categories resource");

        try {
            return Response.ok().entity(getDataAccess().getCategoriesMeta()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage())).build();
        }
    }
    @Override
    public Response categoriesPost(List<String> body, String authorization,  Integer offset,  Integer limit, SecurityContext securityContext) throws NotFoundException {
        LOGGER.info("Input IDs: "+body+" offset: "+offset+" limit: "+limit);
        LOGGER.info("Getting category metadata for entity IDs: " + body +"size: "+body.size());

        try {
            return Response.ok().entity(getCategoryMetadata(body, offset, limit)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage())).build();
        }

    }

    private CategoriesResponse getCategoryMetadata(List<String> categories, final int offset, final int limit) throws Exception {
        CategoriesResponse categoriesResponse = new CategoriesResponse();
        long startTime = System.currentTimeMillis();


        categories = categories.subList(offset, Math.min(offset+limit, categories.size()));
        if (categories != null && !categories.isEmpty()) {
            LOGGER.debug("Using DAO Implementation "+getDataAccess().getClass().getCanonicalName());
            categoriesResponse.setCategories(getDataAccess().getCategoryMetadataByKbId(categories));
        }
        Long responseTime = System.currentTimeMillis() - startTime;

        LOGGER.info("Getting category metadata finished in "+responseTime+" ms");

        return categoriesResponse;
    }
}
