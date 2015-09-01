package org.codelogger.core.context;

import static org.codelogger.utils.StringUtils.isNotBlank;

import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.codelogger.core.context.stereotype.Autowired;
import org.codelogger.core.context.stereotype.Service;
import org.codelogger.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public class ApplicationContextLoader {

  public static ApplicationContextLoader from(final ClassLoader classLoader) {

    return new ApplicationContextLoader(classLoader);
  }

  public ApplicationContext initApplicationContext(final String contextConfigLocation) {

    if (StringUtils.isBlank(contextConfigLocation)) {
      throw new IllegalArgumentException("Argument contextConfigLocation can not be blank.");
    }
    try {
      configurations = new Properties();
      logger.debug("load application context configurations.");
      configurations.load(new InputStreamReader(classLoader
        .getResourceAsStream(contextConfigLocation), UTF_8));
      ClassPath classPath = ClassPath.from(classLoader);
      String valueOfScanPackage = configurations.getProperty(SCAN_PACKAGE);
      ConcurrentHashMap<Class<?>, Object> typeToBean = new ConcurrentHashMap<Class<?>, Object>();
      if (isNotBlank(valueOfScanPackage)) {
        logger.debug("scan components for packages[{}]", valueOfScanPackage);
        String[] basePackages = valueOfScanPackage.split(INIT_PARAM_DELIMITERS);
        Set<ClassInfo> allClassInfo = new LinkedHashSet<ClassInfo>();
        for (String basePackage : basePackages) {
          allClassInfo.addAll(classPath.getTopLevelClassesRecursive(basePackage));
        }
        for (ClassInfo classInfo : allClassInfo) {
          Class<?> load = classInfo.load();
          if (load.isAnnotationPresent(Service.class)) {
            typeToBean.put(load, load.newInstance());
          }
        }
        for (Entry<Class<?>, Object> classWithInstance : typeToBean.entrySet()) {
          Class<?> targetClass = classWithInstance.getKey();
          Object targetInstance = classWithInstance.getValue();
          for (Field field : targetClass.getDeclaredFields()) {
            System.out.println("==========set value==========");
            if (field.isAnnotationPresent(Autowired.class)) {
              logger.debug("field {}", field.getGenericType());
              if (Modifier.isFinal(field.getModifiers())) {
                continue;
              }
              field.setAccessible(true);
              logger.debug("target {}", targetInstance);
              Object value = typeToBean.get(field.getGenericType());
              logger.debug("value {}", value);
              field.set(targetInstance, value);
            }
          }
        }
        System.out.println("==========final==========");
        for (Entry<Class<?>, Object> classWithInstance : typeToBean.entrySet()) {
          logger.debug("{}", classWithInstance);
        }
      }
      return new ApplicationContext(typeToBean);
    } catch (Exception e) {
      logger.error("Init application context failed.", e);
      throw new IllegalArgumentException();
    }
  }

  public ApplicationContextLoader() {

    classLoader = getClass().getClassLoader();
  }

  private ApplicationContextLoader(final ClassLoader classLoader) {

    this.classLoader = classLoader;
  }

  private ClassLoader classLoader;

  private Properties configurations;

  private static final String INIT_PARAM_DELIMITERS = "[,; \t\n]";

  private static final String SCAN_PACKAGE = "scan-package";

  private static final String UTF_8 = "UTF-8";

  private static final Logger logger = LoggerFactory.getLogger(ApplicationContextLoader.class);
}
