package de.mpg.mpi_inf.ambiversenlu.kg.api.factories;

import de.mpg.mpi_inf.ambiversenlu.kg.api.EntitiesApiService;
import de.mpg.mpi_inf.ambiversenlu.kg.api.impl.EntitiesApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-02-21T12:33:39.350Z")
public class EntitiesApiServiceFactory {
    private final static EntitiesApiService service = new EntitiesApiServiceImpl();

    public static EntitiesApiService getEntitiesApi() {
        return service;
    }
}
