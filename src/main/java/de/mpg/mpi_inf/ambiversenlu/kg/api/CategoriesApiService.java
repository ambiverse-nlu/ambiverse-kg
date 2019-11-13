package de.mpg.mpi_inf.ambiversenlu.kg.api;

import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;

import de.mpg.mpi_inf.ambiversenlu.kg.model.CategoryRequest;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import java.util.Map;
import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-10-24T12:54:00.720Z[GMT]")
public abstract class CategoriesApiService {
    private IDao dataAccess;
    public abstract Response categoriesMetaGet(String authorization,SecurityContext securityContext) throws NotFoundException;
    public abstract Response categoriesPost(List<String> body, String authorization, Integer offset, Integer limit,SecurityContext securityContext) throws NotFoundException;

    public IDao getDataAccess() {
        return dataAccess;
    }

    public void setDataAccess(IDao dataAccess) {
        this.dataAccess = dataAccess;
    }
}
