package de.mpg.mpi_inf.ambiversenlu.kg.api.factories;

import de.mpg.mpi_inf.ambiversenlu.kg.api.CategoriesApiService;
import de.mpg.mpi_inf.ambiversenlu.kg.api.impl.CategoriesApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-02-21T12:33:39.350Z")
public class CategoriesApiServiceFactory {
    private final static CategoriesApiService service = new CategoriesApiServiceImpl();

    public static CategoriesApiService getCategoriesApi() {
        return service;
    }
}
