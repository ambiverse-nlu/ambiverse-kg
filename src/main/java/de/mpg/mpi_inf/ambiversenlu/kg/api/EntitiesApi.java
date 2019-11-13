package de.mpg.mpi_inf.ambiversenlu.kg.api;


import de.mpg.mpi_inf.ambiversenlu.kg.api.factories.EntitiesApiServiceFactory;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.model.EntitiesResponse;
import de.mpg.mpi_inf.ambiversenlu.kg.model.EntitiesSearchRequest;
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

import java.math.BigDecimal;

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


@Path("/entities")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-10-24T12:54:00.720Z[GMT]")public class EntitiesApi  {
    private final EntitiesApiService delegate;

    @Inject
    @Named("DaoNeo4j")
    private IDao dataAccess;

    public EntitiesApi(@Context ServletConfig servletContext) {
        EntitiesApiService delegate = null;

        if (servletContext != null) {
            String implClass = servletContext.getInitParameter("EntitiesApi.implementation");
            if (implClass != null && !"".equals(implClass.trim())) {
                try {
                    delegate = (EntitiesApiService) Class.forName(implClass).newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (delegate == null) {
            delegate = EntitiesApiServiceFactory.getEntitiesApi();
        }

        this.delegate = delegate;
    }

    @GET


    @Produces({ "application/json"})
    @Operation(summary = "Entity Metadata.", description = "Retrieves Metadata for collection of entities identified by their knowledge graph IDs.", tags={ "Entities" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = EntitiesResponse.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = MessageResponse.class))) })
    public Response entitiesGet(@Parameter(description = "List of entity ids devided by comma",required=true, schema = @Schema(example = "Q567")) @QueryParam("ids") String ids
            ,
                                @Parameter(description = "Your access token" ) @HeaderParam("Authorization") String authorization

            ,@Parameter(description = "Skip over a number of items by specifying an offset value for the query. ", schema = @Schema(example = "0")) @DefaultValue("0") @QueryParam("offset") Integer offset
            ,@Parameter(description = "Limit the number of items in the response.", schema = @Schema(example = "10")) @DefaultValue("10") @QueryParam("limit") Integer limit
            ,@Context SecurityContext securityContext)
            throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.entitiesGet(ids,authorization,offset,limit,securityContext);
    }
    @GET
    @Path("/{id}")

    @Produces({ "application/json" })
    @Operation(summary = "Entity Metadata.", description = "Retrieves Metadata for single entity identified by its knowledge graph ID.", tags={ "Entities" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = EntitiesResponse.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = MessageResponse.class))) })
    public Response entitiesIdGet(@Parameter(description = "Entity id",required=true, schema = @Schema(example = "Q567")) @PathParam("id") String id
            ,
                                  @Parameter(description = "Your access token" )@HeaderParam("Authorization") String authorization

            ,@Context SecurityContext securityContext)
            throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.entitiesIdGet(id,authorization,securityContext);
    }
    @GET
    @Path("/_meta")

    @Produces({ "application/json" })
    @Operation(summary = "Meta information about the entities.", description = "Retrieves meta information about the entities.", tags={ "Entities" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Meta.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = MessageResponse.class))) })
    public Response entitiesMetaGet(
            @Parameter(description = "Your access token" ) @HeaderParam("Authorization") String authorization

            ,@Context SecurityContext securityContext)
            throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.entitiesMetaGet(authorization,securityContext);
    }
    @POST

    @Consumes({ "application/json" })
    @Produces({ "application/json"})
    @Operation(summary = "Entity Metadata.", description = "Retrieves Metadata for collection of entities identified by their knowledge graph IDs.", tags={ "Entities" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = EntitiesResponse.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = MessageResponse.class))) })
    public Response entitiesPost(@Parameter(description = "" ,required=true, schema = @Schema(example = "[ \"Q567\" ]", description = "List of entity IDs.")) List<String> body

            ,
                                 @Parameter(description = "Your access token" ) @HeaderParam("Authorization") String authorization

            ,@Parameter(description = "Skip over a number of items by specifying an offset value for the query. ", schema = @Schema(example = "0")) @DefaultValue("0") @QueryParam("offset") Integer offset
            ,@Parameter(description = "Limit the number of items in the response.", schema = @Schema(example = "10")) @DefaultValue("10") @QueryParam("limit") Integer limit
            ,@Context SecurityContext securityContext)
            throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.entitiesPost(body,authorization,offset,limit,securityContext);
    }
    @GET
    @Path("/searchByCoordinates")

    @Produces({ "application/json" })
    @Operation(summary = "Entity search.", description = "Searches for entities (identified by their knowledge graph IDs) at a specified point of interest within a given distance.", tags={ "Entities" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(example =
                    "  \"http://www.wikidata.org/entity/Q2253833\",\n" +
                            "  \"http://www.wikidata.org/entity/Q2253808\",\n" +
                            "  \"http://www.wikidata.org/entity/Q1290603\",\n" +
                            "  \"http://www.wikidata.org/entity/Q2232701\",\n" +
                            "  \"http://www.wikidata.org/entity/Q1380896\",\n" +
                            "  \"http://www.wikidata.org/entity/Q2358958\",\n" +
                            "  \"http://www.wikidata.org/entity/Q824180\",\n" +
                            "  \"http://www.wikidata.org/entity/Q1529890\",\n" +
                            "  \"http://www.wikidata.org/entity/Q1518713\"\n"
            )))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = MessageResponse.class))) })
    public Response entitiesSearchByCoordinatesGet(@Parameter(description = "The latitude of a point of interest.",required=true, schema = @Schema(example = "49.257792")) @QueryParam("latitude") BigDecimal latitude
            ,@Parameter(description = "The longitude of a point of interest.",required=true, schema = @Schema(example = "7.046052")) @QueryParam("longitude") BigDecimal longitude
            ,
                                                   @Parameter(description = "Your access token" )@HeaderParam("Authorization") String authorization

            ,@Parameter(description = "The distance to the point of interest.", schema = @Schema(example = "2")) @QueryParam("distance") BigDecimal distance
            ,@Context SecurityContext securityContext)
            throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.entitiesSearchByCoordinatesGet(latitude,longitude,authorization,distance,securityContext);
    }
    @POST
    @Path("/searchByCoordinates")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Entity search.", description = "Searches for entities (identified by their knowledge graph IDs) at a specified point of interest within a given distance.", tags={ "Entities" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(example =
                    "  \"http://www.wikidata.org/entity/Q2253833\",\n" +
                            "  \"http://www.wikidata.org/entity/Q2253808\",\n" +
                            "  \"http://www.wikidata.org/entity/Q1290603\",\n" +
                            "  \"http://www.wikidata.org/entity/Q2232701\",\n" +
                            "  \"http://www.wikidata.org/entity/Q1380896\",\n" +
                            "  \"http://www.wikidata.org/entity/Q2358958\",\n" +
                            "  \"http://www.wikidata.org/entity/Q824180\",\n" +
                            "  \"http://www.wikidata.org/entity/Q1529890\",\n" +
                            "  \"http://www.wikidata.org/entity/Q1518713\"\n"
            )))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = MessageResponse.class))) })
    public Response entitiesSearchByCoordinatesPost(@Parameter(description = "" ,required=true) EntitiesSearchRequest body

            ,
                                                    @Parameter(description = "Your access token" )@HeaderParam("Authorization") String authorization

            ,@Context SecurityContext securityContext)
            throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.entitiesSearchByCoordinatesPost(body,authorization,securityContext);
    }
}
