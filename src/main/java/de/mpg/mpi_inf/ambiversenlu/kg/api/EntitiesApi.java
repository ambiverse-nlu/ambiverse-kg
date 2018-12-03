package de.mpg.mpi_inf.ambiversenlu.kg.api;

import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.api.factories.EntitiesApiServiceFactory;

import de.mpg.mpi_inf.ambiversenlu.kg.model.EntitiesResponse;
import de.mpg.mpi_inf.ambiversenlu.kg.model.EntitiesSearchRequest;
import de.mpg.mpi_inf.ambiversenlu.kg.model.EntityRequest;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Meta;
import io.swagger.annotations.ApiParam;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletConfig;
//import NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

@Path("/entities")

@Produces({ "application/json" })
@io.swagger.annotations.Api(description = "the entities API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-02-23T14:28:08.068Z")
public class EntitiesApi  {
   private final EntitiesApiService delegate;

  @Inject @Named("DaoNeo4j")
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
    
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = EntitiesResponse.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "ambiverse_auth", scopes = {
            
        })
    }, tags={ "entities", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = EntitiesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = EntitiesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Method Not Allowed", response = EntitiesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = EntitiesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 503, message = "Service Unavailable", response = EntitiesResponse.class) })
    public Response entitiesGet(@ApiParam(value = "Your access token" ,required=true)@HeaderParam("Authorization") String authorization
,@ApiParam(value = "List of entity ids devided by comma",required=true) @QueryParam("ids") String ids
,@ApiParam(value = "Skip over a number of items by specifying an offset value for the query. ", defaultValue="0") @DefaultValue("0") @QueryParam("offset") Integer offset
,@ApiParam(value = "Limit the number of items in the response.", defaultValue="10") @DefaultValue("10") @QueryParam("limit") Integer limit
,@Context SecurityContext securityContext)
    throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.entitiesGet(authorization,ids,offset,limit,securityContext);
    }
    @GET
    @Path("/{id}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = EntitiesResponse.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "ambiverse_auth", scopes = {
            
        })
    }, tags={ "entities", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = EntitiesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = EntitiesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Method Not Allowed", response = EntitiesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = EntitiesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 503, message = "Service Unavailable", response = EntitiesResponse.class) })
    public Response entitiesIdGet(@ApiParam(value = "Your access token" ,required=true)@HeaderParam("Authorization") String authorization
,@ApiParam(value = "Entity id",required=true) @PathParam("id") String id
,@Context SecurityContext securityContext)
    throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.entitiesIdGet(authorization,id,securityContext);
    }
    @GET
    @Path("/_meta")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = Meta.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "ambiverse_auth", scopes = {
            
        })
    }, tags={ "entities", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = Meta.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = Meta.class),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Method Not Allowed", response = Meta.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = Meta.class),
        
        @io.swagger.annotations.ApiResponse(code = 503, message = "Service Unavailable", response = Meta.class) })
    public Response entitiesMetaGet(@ApiParam(value = "Your access token" ,required=true)@HeaderParam("Authorization") String authorization
,@Context SecurityContext securityContext)
    throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.entitiesMetaGet(authorization,securityContext);
    }
    @POST
    
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Retrieves the collection of entities identified by their knowledge graph IDs. ", response = EntitiesResponse.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "ambiverse_auth", scopes = {
            
        })
    }, tags={ "entities", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = EntitiesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = EntitiesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Method Not Allowed", response = EntitiesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = EntitiesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 503, message = "Service Unavailable", response = EntitiesResponse.class) })
    public Response entitiesPost(@ApiParam(value = "Your access token" ,required=true)@HeaderParam("Authorization") String authorization
,@ApiParam(value = "" ,required=true) EntityRequest body
,@ApiParam(value = "Skip over a number of items by specifying an offset value for the query. ", defaultValue="0") @DefaultValue("0") @QueryParam("offset") Integer offset
,@ApiParam(value = "Limit the number of items in the response.", defaultValue="10") @DefaultValue("10") @QueryParam("limit") Integer limit
,@Context SecurityContext securityContext)
    throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.entitiesPost(authorization,body,offset,limit,securityContext);
    }
    @GET
    @Path("/searchByCoordinates")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Searches for entities (identified by their knowledge graph IDs) at a specified point of interest within a given distance.", response = String.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "ambiverse_auth", scopes = {
            
        })
    }, tags={ "entities", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = String.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Method Not Allowed", response = String.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = String.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 503, message = "Service Unavailable", response = String.class, responseContainer = "List") })
    public Response entitiesSearchByCoordinatesGet(@ApiParam(value = "Your access token" ,required=true)@HeaderParam("Authorization") String authorization
,@ApiParam(value = "The latitude of a point of interest.",required=true) @QueryParam("latitude") BigDecimal latitude
,@ApiParam(value = "The longitude of a point of interest.",required=true) @QueryParam("longitude") BigDecimal longitude
,@ApiParam(value = "The distance to the point of interest.") @QueryParam("distance") BigDecimal distance
,@Context SecurityContext securityContext)
    throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.entitiesSearchByCoordinatesGet(authorization,latitude,longitude,distance,securityContext);
    }
    @POST
    @Path("/searchByCoordinates")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Searches for entities (identified by their knowledge graph IDs) at a specified point of interest within a given distance.", response = String.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "ambiverse_auth", scopes = {
            
        })
    }, tags={ "entities", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = String.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Method Not Allowed", response = String.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = String.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 503, message = "Service Unavailable", response = String.class, responseContainer = "List") })
    public Response entitiesSearchByCoordinatesPost(@ApiParam(value = "Your access token" ,required=true)@HeaderParam("Authorization") String authorization
,@ApiParam(value = "" ,required=true) EntitiesSearchRequest body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.entitiesSearchByCoordinatesPost(authorization,body,securityContext);
    }
}
