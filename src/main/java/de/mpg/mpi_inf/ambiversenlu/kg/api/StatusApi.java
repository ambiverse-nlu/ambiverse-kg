package de.mpg.mpi_inf.ambiversenlu.kg.api;


import de.mpg.mpi_inf.ambiversenlu.kg.api.factories.StatusApiServiceFactory;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.model.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletConfig;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


@Path("/status")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-10-24T12:54:00.720Z[GMT]")
public class StatusApi  {
    private final StatusApiService delegate;

    @Inject
    @Named("DaoNeo4j")
    private IDao dataAccess;

    public StatusApi(@Context ServletConfig servletContext) {
        StatusApiService delegate = null;

        if (servletContext != null) {
            String implClass = servletContext.getInitParameter("StatusApi.implementation");
            if (implClass != null && !"".equals(implClass.trim())) {
                try {
                    delegate = (StatusApiService) Class.forName(implClass).newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (delegate == null) {
            delegate = StatusApiServiceFactory.getStatusApi();
        }

        this.delegate = delegate;
    }

    @GET


    @Produces({ "application/json" })
    @Operation(summary = "Status", description = "Returns status information about the service.", tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = MessageResponse.class))) })
    public Response statusGet(@Context SecurityContext securityContext)
            throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.statusGet(securityContext);
    }
}
