package de.mpg.mpi_inf.ambiversenlu.kg.config;

import org.slf4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class Neo4jConfig {
  private static final Logger LOGGER =
      org.slf4j.LoggerFactory.getLogger(Neo4jConfig.class);

  private Properties properties;

  private static Neo4jConfig config = null;

  private Neo4jConfig() {
    try {
      properties = ConfigUtils.loadProperties("neo4j.properties");
    } catch (IOException e) {
      LOGGER.error("Could not load Elasticsearch Config file: " + e.getLocalizedMessage());
    }
  }

  private static Neo4jConfig getInstance() {
    if (config == null) {
      config = new Neo4jConfig();
    }
    return config;
  }

  private String getValue(String key) {
    return properties.getProperty(key);
  }

  private void setValue(String key, String value) {
    properties.setProperty(key, value);
  }

  public static String get(String key) {
    String value = Neo4jConfig.getInstance().getValue(key);
    if (value == null) {
      LOGGER.error("Missing key in properties file with no default value: " + key);
    }
    return value;
  }

  public static int getAsInt(String key) {
    String value = get(key);
    return Integer.parseInt(value);
  }
}
