package org.codelogger.core.context.bean;

import static org.codelogger.utils.StringUtils.isNotBlank;
import static org.codelogger.utils.lang.CharacterEncoding.UTF_8;

import java.io.InputStreamReader;
import java.util.Properties;

import org.codelogger.core.exception.ResourceNotFoundExcception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesLoader {

  public static Properties loadProperties(final String propertiesLocation) {

    if (isNotBlank(propertiesLocation)) {
      try {
        logger.debug("load properties from:'{}'.", propertiesLocation);
        Properties configurations = new Properties();
        configurations.load(new InputStreamReader(PropertiesLoader.class.getClassLoader()
          .getResourceAsStream(propertiesLocation), UTF_8.getCharsetName()));
        properties.putAll(configurations);
        return configurations;
      } catch (Exception e) {
        throw new ResourceNotFoundExcception(e);
      }
    }
    return System.getProperties();
  }

  public static String getProperty(final String key) {

    if (key == null) {
      return null;
    } else {
      String value = properties.getProperty(key);
      return value == null ? System.getProperty(key) : value;
    }
  }

  private static final Properties properties = new Properties();

  private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
}
