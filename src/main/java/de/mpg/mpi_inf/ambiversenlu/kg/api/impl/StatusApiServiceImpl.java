package de.mpg.mpi_inf.ambiversenlu.kg.api.impl;


import de.mpg.mpi_inf.ambiversenlu.kg.api.ApiResponseMessage;
import de.mpg.mpi_inf.ambiversenlu.kg.api.NotFoundException;
import de.mpg.mpi_inf.ambiversenlu.kg.api.StatusApiService;
import de.mpg.mpi_inf.ambiversenlu.kg.model.EntitiesResponse;
import org.slf4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-10-24T12:54:00.720Z[GMT]")
public class StatusApiServiceImpl extends StatusApiService {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StatusApiServiceImpl.class);

    @Override
    public Response statusGet(SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        LOGGER.info("Checking the status of the service with a dummy call for id 'Q567`");
        List<String> idList = new ArrayList<>();
        idList.add("http://www.wikidata.org/entity/Q567");

        LOGGER.info("Getting entity metadata for entity IDs: " + idList +"size: "+idList.size());

        EntitiesResponse response = new EntitiesResponse();

        try {
            if (idList != null && !idList.isEmpty()) {
                LOGGER.debug("Using DAO Implementation "+getDataAccess().getClass().getCanonicalName());
                response.setEntities(getDataAccess().getEntityMetadataByKbId(idList));
                return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "The server is up and running")).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage())).build();
        }
        return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "We couldn't check if the service is up and running, so restarting...")).build();
    }
}
