package de.mpg.mpi_inf.ambiversenlu.kg.api;

import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.impl.DaoNeo4j;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class Hk2Feature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {

                bind(DaoNeo4j.class).to(IDao.class)
                    .named("DaoNeo4j").in(RequestScoped.class);

            }
        };
        context.register(binder);
        return true;
    }
}