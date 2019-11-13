package de.mpg.mpi_inf.ambiversenlu.kg.api;


import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-10-24T12:54:00.720Z[GMT]")
public abstract class StatusApiService {
    private IDao dataAccess;
    public abstract Response statusGet(SecurityContext securityContext) throws NotFoundException;


    public IDao getDataAccess() {
        return dataAccess;
    }

    public void setDataAccess(IDao dataAccess) {
        this.dataAccess = dataAccess;
    }
}
