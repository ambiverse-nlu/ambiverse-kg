package de.mpg.mpi_inf.ambiversenlu.kg.api.factories;


import de.mpg.mpi_inf.ambiversenlu.kg.api.StatusApiService;
import de.mpg.mpi_inf.ambiversenlu.kg.api.impl.StatusApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-10-24T12:54:00.720Z[GMT]")
public class StatusApiServiceFactory {
    private final static StatusApiService service = new StatusApiServiceImpl();

    public static StatusApiService getStatusApi() {
        return service;
    }
}
