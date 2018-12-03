package de.mpg.mpi_inf.ambiversenlu.kg.api;

import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.api.factories.CategoriesApiServiceFactory;

import de.mpg.mpi_inf.ambiversenlu.kg.model.CategoriesResponse;
import de.mpg.mpi_inf.ambiversenlu.kg.model.CategoryRequest;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Meta;
import io.swagger.annotations.ApiParam;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

@Path("/categories")

@Produces({ "application/json" })
@io.swagger.annotations.Api(description = "the categories API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-02-21T12:33:39.350Z")
public class CategoriesApi  {
   private final CategoriesApiService delegate;

  @Inject @Named("DaoNeo4j")
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
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = Meta.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "ambiverse_auth", scopes = {
            
        })
    }, tags={ "categories", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = Meta.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = Meta.class),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Method Not Allowed", response = Meta.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = Meta.class),
        
        @io.swagger.annotations.ApiResponse(code = 503, message = "Service Unavailable", response = Meta.class) })
    public Response categoriesMetaGet(@ApiParam(value = "Your access token" ,required=true)@HeaderParam("Authorization") String authorization
,@Context SecurityContext securityContext)
    throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.categoriesMetaGet(authorization,securityContext);
    }
    @POST
    
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Retrieves the collection of categories identified by their knowledge graph IDs. Use this method for batch fetching of categories. ", response = CategoriesResponse.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "ambiverse_auth", scopes = {
            
        })
    }, tags={ "categories", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = CategoriesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = CategoriesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Method Not Allowed", response = CategoriesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = CategoriesResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 503, message = "Service Unavailable", response = CategoriesResponse.class) })
    public Response categoriesPost(@ApiParam(value = "Your access token" ,required=true)@HeaderParam("Authorization") String authorization
,@ApiParam(value = "" ,required=true) CategoryRequest body
,@ApiParam(value = "Skip over a number of items by specifying an offset value for the query.", defaultValue="0") @DefaultValue("0") @QueryParam("offset") Integer offset
,@ApiParam(value = "Limit the number of items in the response.", defaultValue="10") @DefaultValue("10") @QueryParam("limit") Integer limit
,@Context SecurityContext securityContext)
    throws NotFoundException {
        this.delegate.setDataAccess(dataAccess);
        return delegate.categoriesPost(authorization,body,offset,limit,securityContext);
    }
}
