package de.mpg.mpi_inf.ambiversenlu.kg;

import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;
import org.slf4j.Logger;

import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.dao.impl.DaoNeo4j;


@ApplicationPath("/knowledgegraph/")
public class KGApiApplication extends ResourceConfig {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(KGApiApplication.class);

    public KGApiApplication()  {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {

                bind(DaoNeo4j.class).to(IDao.class)
                    .named("DaoNeo4j").in(RequestScoped.class);

            }
        };


        LOGGER.info("Creating KGApiApplication");
        register(binder);
        packages(true, "io.swagger.jaxrs.listing,de.mpg.mpi_inf.ambiversenlu.kg.api");
        //register(EntitiesApi.class);
        //register(CategoriesApi.class);

        // Enable gzip encoding
        // Inspired by http://www.codingpedia.org/ama/how-to-compress-responses-in-java-rest-api-with-gzip-and-jersey/
        EncodingFilter.enableFor(this, GZipEncoder.class);
        //LOGGER.info("Initializing the AIDA Manager");
        //EntityLinkingConfig.set(EntityLinkingConfig.DATAACESS_CACHE_PRELOAD, "false");
        //EntityLinkingManager.init();
    }
}
