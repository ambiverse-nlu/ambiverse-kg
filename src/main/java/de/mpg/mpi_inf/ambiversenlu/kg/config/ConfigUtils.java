package de.mpg.mpi_inf.ambiversenlu.kg.config;

import de.mpg.mpi_inf.ambiversenlu.kg.util.ClassPathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Utility methods for managing configuration through properties.
 */
public class ConfigUtils {

  private static Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);

  public static final String ENV_CONF_FLAG = "CONF";

  public static final String CONF_FLAG = "conf.conf";

  public static final String CONF_PATH = "conf.properties";

  public static final String DEFAULT_CONF = "default";

  public static Properties loadProperties(String fileName) throws IOException {

    // Load the defaults.
    Properties defaultProperties;
    try {
      String defaultPath = DEFAULT_CONF + "/" + fileName;
      defaultProperties = ClassPathUtils.getPropertiesFromClasspath(defaultPath);
    } catch (IOException e) {
      throw new IOException("Default settings file missing. This should not happen!");
    }

    // Conf can override defaults.
    String conf = null;

    // Try if aida_env.properties specifies non-default aida.conf
    Properties envProperties = ClassPathUtils.getPropertiesFromClasspath(CONF_PATH);
    if (envProperties != null && envProperties.containsKey(CONF_FLAG)) {
      String env = envProperties.getProperty(CONF_FLAG);
      if (!env.equals(DEFAULT_CONF)) {
        conf = env;
        LOGGER.info("Configuration '" + conf + "' [read from " + CONF_PATH + "].");
      }
    }

    // Override by Environment
    if (System.getenv().containsKey(ENV_CONF_FLAG)) {
      conf = System.getenv(ENV_CONF_FLAG);
      LOGGER.info("Configuration '" + conf + "' [set by environment " + ENV_CONF_FLAG + "].");
    }

    // Override by System property.
    if (System.getProperty(CONF_FLAG) != null) {
      conf = System.getProperty(CONF_FLAG);
      LOGGER.info("Configuration '" + conf + "' [set by -D" + CONF_FLAG + "].");
    }

    if (conf == null) {
      LOGGER.info("Using default configuration for '" + fileName + "', pass -D" + CONF_FLAG +
              " or set in '" + CONF_PATH + "' or by environment variable '" + ENV_CONF_FLAG + "' to change.");
      return defaultProperties;
    }

    Properties confProperties;
    try {
      String confPath = conf + "/" + fileName;
      confProperties = ClassPathUtils.getPropertiesFromClasspath(confPath, defaultProperties);
      if (confProperties == null) {
        // No specific overwrites found, use defaults.
        LOGGER.warn("Config '" + conf + "' was specified but did not contain overwrites for '" + fileName + "'. "
                + "This is ok if you do not want to overwrite any settings in this file, but could mean a mistyped aida.conf.");
        return defaultProperties;
      }
    } catch (IOException e) {
      throw new IOException("Could not read '" + fileName + "' from configuration '" + conf + "'.");
    }

    return confProperties;
  }
}
