package de.mpg.mpi_inf.ambiversenlu.kg.api.factories;


import de.mpg.mpi_inf.ambiversenlu.kg.api.CategoriesApiService;
import de.mpg.mpi_inf.ambiversenlu.kg.api.impl.CategoriesApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-10-24T12:54:00.720Z[GMT]")
public class CategoriesApiServiceFactory {
    private final static CategoriesApiService service = new CategoriesApiServiceImpl();

    public static CategoriesApiService getCategoriesApi() {
        return service;
    }
}
