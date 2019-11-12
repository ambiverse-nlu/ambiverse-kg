package de.mpg.mpi_inf.ambiversenlu.kg.api;

import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;

import de.mpg.mpi_inf.ambiversenlu.kg.model.EntitiesSearchRequest;
import de.mpg.mpi_inf.ambiversenlu.kg.model.EntityRequest;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.math.BigDecimal;

import java.util.Map;
import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-10-24T12:54:00.720Z[GMT]")
public abstract class EntitiesApiService {
    private IDao dataAccess;

    public abstract Response entitiesGet( @NotNull String ids,String authorization, Integer offset, Integer limit,SecurityContext securityContext) throws NotFoundException;
    public abstract Response entitiesIdGet(String id,String authorization,SecurityContext securityContext) throws NotFoundException;
    public abstract Response entitiesMetaGet(String authorization,SecurityContext securityContext) throws NotFoundException;
    public abstract Response entitiesPost(List<String> body,String authorization, Integer offset, Integer limit,SecurityContext securityContext) throws NotFoundException;
    public abstract Response entitiesSearchByCoordinatesGet( @NotNull BigDecimal latitude, @NotNull BigDecimal longitude,String authorization, BigDecimal distance,SecurityContext securityContext) throws NotFoundException;
    public abstract Response entitiesSearchByCoordinatesPost(EntitiesSearchRequest body,String authorization,SecurityContext securityContext) throws NotFoundException;

    public IDao getDataAccess() {
        return dataAccess;
    }

    public void setDataAccess(IDao dataAccess) {
        this.dataAccess = dataAccess;
    }
}
