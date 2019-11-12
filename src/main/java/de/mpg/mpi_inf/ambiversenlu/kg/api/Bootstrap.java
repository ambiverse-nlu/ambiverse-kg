package de.mpg.mpi_inf.ambiversenlu.kg.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "KG Web Service",
                version = "v2",
                description = "The Knowledge Graph Web Service lets you find entities and categories in the knowledge graph. This service is particularly suited to be run as a second step after the NLU Service has been used for linking ambiguous names in natural-language texts to entities. With this service, you can now explore these entities further. ",
                termsOfService = "https://data-protection.mpi-klsb.mpg.de/inf/ambiversenlu.mpi-inf.mpg.de",
                contact = @Contact(email = "ambiversenlu-admin@mpi-inf.mpg.de")
        )
)
public class Bootstrap {
}
