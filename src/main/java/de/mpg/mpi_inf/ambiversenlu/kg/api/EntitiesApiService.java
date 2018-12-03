package de.mpg.mpi_inf.ambiversenlu.kg.api;

import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;

import de.mpg.mpi_inf.ambiversenlu.kg.model.EntitiesSearchRequest;
import de.mpg.mpi_inf.ambiversenlu.kg.model.EntityRequest;

import java.math.BigDecimal;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-02-23T14:28:08.068Z")
public abstract class EntitiesApiService {
    private IDao dataAccess;

    public abstract Response entitiesGet(String authorization, @NotNull String ids, Integer offset, Integer limit,SecurityContext securityContext) throws NotFoundException;
    public abstract Response entitiesIdGet(String authorization,String id,SecurityContext securityContext) throws NotFoundException;
    public abstract Response entitiesMetaGet(String authorization,SecurityContext securityContext) throws NotFoundException;
    public abstract Response entitiesPost(String authorization, EntityRequest body, Integer offset, Integer limit,SecurityContext securityContext) throws NotFoundException;
    public abstract Response entitiesSearchByCoordinatesGet(String authorization, @NotNull BigDecimal latitude, @NotNull BigDecimal longitude, BigDecimal distance,SecurityContext securityContext) throws NotFoundException;
    public abstract Response entitiesSearchByCoordinatesPost(String authorization, EntitiesSearchRequest body,SecurityContext securityContext) throws NotFoundException;

    public IDao getDataAccess() {
        return dataAccess;
    }

    public void setDataAccess(IDao dataAccess) {
        this.dataAccess = dataAccess;
    }
}
