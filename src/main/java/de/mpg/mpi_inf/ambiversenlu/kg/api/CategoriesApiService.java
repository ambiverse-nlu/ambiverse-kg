package de.mpg.mpi_inf.ambiversenlu.kg.api;

import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;

import de.mpg.mpi_inf.ambiversenlu.kg.model.CategoryRequest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-02-21T12:33:39.350Z")
public abstract class CategoriesApiService {
    private IDao dataAccess;
    public abstract Response categoriesMetaGet(String authorization,SecurityContext securityContext) throws NotFoundException;
    public abstract Response categoriesPost(String authorization, CategoryRequest body, Integer offset, Integer limit,SecurityContext securityContext) throws NotFoundException;

    public IDao getDataAccess() {
        return dataAccess;
    }

    public void setDataAccess(IDao dataAccess) {
        this.dataAccess = dataAccess;
    }
}
