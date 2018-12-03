package de.mpg.mpi_inf.ambiversenlu.kg.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClassPathUtils {

  private static Logger logger_ = LoggerFactory.getLogger(ClassPathUtils.class);

  public ClassPathUtils() {

  }

  public static Properties getPropertiesFromClasspath(String propFileName)
      throws IOException {
    return getPropertiesFromClasspath(propFileName, null);
  }

  /**
   * Reads the specified properties file from the classpath. Returns null if it does not exist.
   *
   * @param propFileName
   * @param defaultProperties
   * @return  The specified properties, null if they do not exist.
   * @throws IOException
   */
  public static Properties getPropertiesFromClasspath(String propFileName, Properties defaultProperties) throws IOException {
    Properties props = new Properties(defaultProperties);
    InputStream inputStream = ClassPathUtils.class.getClassLoader()
        .getResourceAsStream(propFileName);

    if (inputStream == null) {
      return null;
    } else {
      props.load(inputStream);
      inputStream.close();
      return props;
    }
  }


}
