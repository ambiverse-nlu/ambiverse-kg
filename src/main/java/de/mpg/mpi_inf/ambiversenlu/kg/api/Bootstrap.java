package de.mpg.mpi_inf.ambiversenlu.kg.api;

import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.*;

import io.swagger.models.auth.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class Bootstrap extends HttpServlet {
  @Override
  public void init(ServletConfig config) throws ServletException {
    Info info = new Info()
      .title("Swagger Server")
      .description("The AmbiverseNLU Knowledge Graph Service lets you find entities and categories in the knowledge graph. This service is particularly suited to be run as a second step after the AmbiverseNLU Service has been used for linking ambiguous names in natural-language texts to entities. With this service, you can now explore these entities further. ")
      .termsOfService("")
      .contact(new Contact()
        .email("ambiversenlu-admin@mpi-inf.mpg.de"))
      .license(new License()
        .name("Apache License, Version 2.0")
        .url("https://www.apache.org/licenses/LICENSE-2.0.html"));

    ServletContext context = config.getServletContext();
    Swagger swagger = new Swagger().info(info);

    new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);
  }
}
