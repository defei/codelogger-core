package org.codelogger.core.context;

import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.codelogger.core.context.bean.ComponentScanner;
import org.codelogger.core.context.exception.ContextInitException;
import org.codelogger.core.context.stereotype.Dao;
import org.codelogger.core.context.stereotype.Service;
import org.codelogger.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class ApplicationContextLoader {

  public static ApplicationContextLoader from(final ClassLoader classLoader) {

    return new ApplicationContextLoader(classLoader);
  }

  public ApplicationContext initApplicationContext(final String contextConfigLocation)
    throws ContextInitException {

    try {
      Properties configurations = new Properties();
      logger.debug("load application context configurations from:'{}'.", contextConfigLocation);
      configurations.load(new InputStreamReader(classLoader
        .getResourceAsStream(contextConfigLocation), UTF_8));

      ApplicationContext processedApplicationContext = applicationContext;
      String dependencies = configurations.getProperty(DEPENDENCY_CONFIG);
      if (StringUtils.isNotBlank(dependencies)) {
        String[] dependencyConfigLocations = dependencies.split(INIT_PARAM_DELIMITERS);
        for (String dependencyConfigLocation : dependencyConfigLocations) {
          ApplicationContextLoader applicationContextLoader = new ApplicationContextLoader(
            processedApplicationContext, classLoader, componentScanner.getComponentTypes());
          processedApplicationContext = applicationContextLoader
            .initApplicationContext(dependencyConfigLocation);
        }
      }
      ConcurrentHashMap<Class<?>, Object> typeToBean = processedApplicationContext == null ? componentScanner
        .scan(configurations) : componentScanner.scan(configurations,
        processedApplicationContext.typeToBean);
      System.out.println("!!!!!");
      for (Entry<Class<?>, Object> typeAndBean : typeToBean.entrySet()) {
        System.out.println(typeAndBean.getKey());
        System.out.println(typeAndBean.getValue());
      }
      return new ApplicationContext(processedApplicationContext, typeToBean, configurations);
    } catch (Exception e) {
      logger.error("Init application context failed.", e);
      throw new ContextInitException(e);
    }
  }

  public ApplicationContextLoader() {

    classLoader = getClass().getClassLoader();
    componentScanner = new ComponentScanner(classLoader, null, Service.class, Dao.class);
  }

  public ApplicationContextLoader(final ApplicationContext applicationContext,
    final ClassLoader classLoader) {

    this.classLoader = classLoader;
    this.applicationContext = applicationContext;
    componentScanner = new ComponentScanner(classLoader, applicationContext == null ? null
      : applicationContext.typeToBean, Service.class, Dao.class);
  }

  public ApplicationContextLoader(final ApplicationContext applicationContext,
    final ClassLoader classLoader, final Class<? extends Annotation>... componentTypes) {

    this.classLoader = classLoader;
    this.applicationContext = applicationContext;
    componentScanner = new ComponentScanner(classLoader, applicationContext == null ? null
      : applicationContext.typeToBean, componentTypes);
  }

  private ApplicationContextLoader(final ClassLoader classLoader) {

    this.classLoader = classLoader;
    componentScanner = new ComponentScanner(classLoader, null, Service.class, Dao.class);
  }

  protected void setComponentScanner(final ComponentScanner componentScanner) {

    this.componentScanner = componentScanner;
  }

  private ComponentScanner componentScanner;

  private ApplicationContext applicationContext;

  private ClassLoader classLoader;

  public static final String DEPENDENCY_CONFIG = "dependency-config";

  private static final String INIT_PARAM_DELIMITERS = "[,; \t\n]";

  private static final String UTF_8 = "UTF-8";

  private static final Logger logger = LoggerFactory.getLogger(ApplicationContextLoader.class);
}
