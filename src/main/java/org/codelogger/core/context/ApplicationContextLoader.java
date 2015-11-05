package org.codelogger.core.context;

import static org.codelogger.utils.StringUtils.isBlank;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.codelogger.core.context.bean.ComponentScanner;
import org.codelogger.core.context.bean.DefaultConstructFactory;
import org.codelogger.core.context.bean.PropertiesLoader;
import org.codelogger.core.context.exception.ContextInitException;
import org.codelogger.core.context.stereotype.Dao;
import org.codelogger.core.context.stereotype.Service;
import org.codelogger.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationContextLoader {

  public static ApplicationContextLoader from(final ClassLoader classLoader) {

    return new ApplicationContextLoader(classLoader);
  }

  public ApplicationContext initApplicationContext(final String contextConfigLocation)
    throws ContextInitException {

    try {
      configurations = PropertiesLoader.loadProperties(contextConfigLocation);

      ApplicationContext processedApplicationContext = applicationContext;
      String dependencies = configurations.getProperty(DEPENDENCY_CONFIG);
      if (StringUtils.isNotBlank(dependencies)) {
        String[] dependencyConfigLocations = dependencies.split(INIT_PARAM_DELIMITERS);
        for (String dependencyConfigLocation : dependencyConfigLocations) {
          logger.info("init dependency application context with config:'{}'.",
            dependencyConfigLocation);
          ApplicationContextLoader applicationContextLoader = new ApplicationContextLoader(
            processedApplicationContext, classLoader, getSupportComponentTypeToConstructFactory());
          processedApplicationContext = applicationContextLoader
            .initApplicationContext(dependencyConfigLocation);
        }
      }
      ComponentScanner componentScanner = new ComponentScanner(classLoader,
        applicationContext == null ? null : applicationContext.typeToBean,
        getSupportComponentTypeToConstructFactory());
      ConcurrentHashMap<Class<?>, Object> typeToBean = processedApplicationContext == null ? componentScanner
        .scan(configurations) : componentScanner.scan(configurations,
        processedApplicationContext.typeToBean);
      return new ApplicationContext(processedApplicationContext, typeToBean, configurations);
    } catch (Exception e) {
      logger.error("Init application context failed.", e);
      throw new ContextInitException(e);
    }
  }

  public ApplicationContextLoader() {

    classLoader = getClass().getClassLoader();
  }

  public ApplicationContextLoader(final ApplicationContext applicationContext,
    final ClassLoader classLoader) {

    this.classLoader = classLoader;
    this.applicationContext = applicationContext;
  }

  public ApplicationContextLoader(
    final ApplicationContext applicationContext,
    final ClassLoader classLoader,
    final ConcurrentHashMap<Class<? extends Annotation>, DefaultConstructFactory> componentTypeToConstructFactory) {

    this.classLoader = classLoader;
    this.applicationContext = applicationContext;

  }

  private ApplicationContextLoader(final ClassLoader classLoader) {

    this.classLoader = classLoader;
  }

  protected ConcurrentHashMap<Class<? extends Annotation>, DefaultConstructFactory> getSupportComponentTypeToConstructFactory() {

    ConcurrentHashMap<Class<? extends Annotation>, DefaultConstructFactory> componentTypeToConstructFactory = new ConcurrentHashMap<Class<? extends Annotation>, DefaultConstructFactory>();
    DefaultConstructFactory defaultConstructFactory = new DefaultConstructFactory(configurations);
    componentTypeToConstructFactory.put(Service.class, defaultConstructFactory);
    String daoConstructFactory = configurations.getProperty(DAO_CONSTRUCT_FACTORY);
    try {
      if (isBlank(daoConstructFactory)) {
        componentTypeToConstructFactory.put(Dao.class, defaultConstructFactory);
      } else {
        Constructor<?> constructor = Class.forName(daoConstructFactory).getConstructor(
          Properties.class);
        componentTypeToConstructFactory.put(Dao.class,
          (DefaultConstructFactory) constructor.newInstance(configurations));
      }
    } catch (Exception e) {
      throw new ContextInitException(e);
    }
    return componentTypeToConstructFactory;
  }

  private ApplicationContext applicationContext;

  private ClassLoader classLoader;

  private Properties configurations;

  public static final String DAO_CONSTRUCT_FACTORY = "dao-construct-factory";

  public static final String DEPENDENCY_CONFIG = "dependency-config";

  private static final String INIT_PARAM_DELIMITERS = "[,; \t\n]";

  private static final Logger logger = LoggerFactory.getLogger(ApplicationContextLoader.class);
}
