package de.mpg.mpi_inf.ambiversenlu.kg.api;


import de.mpg.mpi_inf.ambiversenlu.kg.api.factories.CategoriesApiServiceFactory;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.model.CategoriesResponse;
import de.mpg.mpi_inf.ambiversenlu.kg.model.MessageResponse;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Meta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


import java.util.Map;
import java.util.List;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;


@Path("/categories")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-10-24T12:54:00.720Z[GMT]")
public class CategoriesApi  {
    private final CategoriesApiService delegate;

    @Inject
    @Named("DaoNeo4j")
    private IDao dataAccess;

    public CategoriesApi(@Context ServletConfig servletContext) {
        CategoriesApiService delegate = null;

        if (servletContext != null) {
            String implClass = servletContext.getInitParameter("CategoriesApi.implementation");
            if (implClass != null && !"".equals(implClass.trim())) {
                try {
                    delegate = (CategoriesApiService) Class.forName(implClass).newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (delegate == null) {
            delegate = CategoriesApiServiceFactory.getCategoriesApi();
        }

        this.delegate = delegate;
    }

    @GET
    @Path("/_meta")

    @Produces({ "application/json"})
    @Operation(summary = "Meta information about the categories.", description = "Retrieves a meta information about the categories.", tags={ "Categories" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Meta.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = MessageResponse.class))) })
    public Response categoriesMetaGet(
            @Parameter(description = "Your access token" )@HeaderParam("Authorization") String authorization

            ,@Context SecurityContext securityContext)
            throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.categoriesMetaGet(authorization,securityContext);
    }
    @POST

    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Category Metadata.", description = "Retrieves a collection  of metadata for categories identified by their knowledge graph IDs. Use this method for batch fetching of categories. ", tags={ "Categories" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CategoriesResponse.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = MessageResponse.class))) })
    public Response categoriesPost(@Parameter(description = "List of category IDs." ,required=true, schema = @Schema(example = "[ \"YAGO3:<wikicat_Billionaires>\" ]", description = "List of category IDs.")) List<String> body

            ,
                                   @Parameter(description = "Your access token" )@HeaderParam("Authorization") String authorization

            ,@Parameter(description = "Skip over a number of items by specifying an offset value for the query.", schema = @Schema(example = "0")) @QueryParam("offset") @DefaultValue("0") Integer offset
            ,@Parameter(description = "Limit the number of items in the response.", schema = @Schema(example = "10")) @QueryParam("limit") @DefaultValue("10") Integer limit
            ,@Context SecurityContext securityContext)
            throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.categoriesPost(body,authorization,offset,limit,securityContext);
    }
}
