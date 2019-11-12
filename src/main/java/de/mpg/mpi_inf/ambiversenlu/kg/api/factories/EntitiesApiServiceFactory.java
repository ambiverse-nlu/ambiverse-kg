package de.mpg.mpi_inf.ambiversenlu.kg.api.factories;


import de.mpg.mpi_inf.ambiversenlu.kg.api.EntitiesApiService;
import de.mpg.mpi_inf.ambiversenlu.kg.api.impl.EntitiesApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-10-24T12:54:00.720Z[GMT]")
public class EntitiesApiServiceFactory {
    private final static EntitiesApiService service = new EntitiesApiServiceImpl();

    public static EntitiesApiService getEntitiesApi() {
        return service;
    }
}
