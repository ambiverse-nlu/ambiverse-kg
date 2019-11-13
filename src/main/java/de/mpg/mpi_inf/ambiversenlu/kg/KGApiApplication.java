package de.mpg.mpi_inf.ambiversenlu.kg;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;
import org.slf4j.Logger;

import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.impl.DaoNeo4j;


@ApplicationPath("/")
public class KGApiApplication extends ResourceConfig {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(KGApiApplication.class);

    public KGApiApplication()  {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(DaoNeo4j.class).to(IDao.class)
                        .named("DaoNeo4j").in(Singleton.class);

            }
        };


        LOGGER.info("Creating KGApiApplication");
        register(binder);
        packages(true, "io.swagger.jaxrs.listing,de.mpg.mpi_inf.ambiversenlu.kg.api");

        // Enable gzip encoding
        // Inspired by http://www.codingpedia.org/ama/how-to-compress-responses-in-java-rest-api-with-gzip-and-jersey/
        EncodingFilter.enableFor(this, GZipEncoder.class);
    }
}
