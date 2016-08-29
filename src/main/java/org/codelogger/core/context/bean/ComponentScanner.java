package org.codelogger.core.context.bean;

import static org.codelogger.utils.StringUtils.isNotBlank;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.codelogger.core.context.stereotype.Autowired;
import org.codelogger.core.context.stereotype.PostConstruct;
import org.codelogger.core.context.stereotype.Value;
import org.codelogger.utils.MapUtils;
import org.codelogger.utils.ValueUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public class ComponentScanner {

  public ComponentScanner(
    final ConcurrentHashMap<Class<?>, Object> typeToBean,
    final ConcurrentHashMap<Class<? extends Annotation>, DefaultConstructFactory> componentTypeToConstructFactory) {

    this.typeToBean = typeToBean == null ? new ConcurrentHashMap<Class<?>, Object>() : typeToBean;
    this.componentTypeToConstructFactory = componentTypeToConstructFactory;
    classLoader = getClass().getClassLoader();
  }

  public ComponentScanner(
    final ClassLoader classLoader,
    final ConcurrentHashMap<Class<?>, Object> typeToBean,
    final ConcurrentHashMap<Class<? extends Annotation>, DefaultConstructFactory> componentTypeToConstructFactory) {

    this.classLoader = classLoader;
    this.typeToBean = typeToBean == null ? new ConcurrentHashMap<Class<?>, Object>() : typeToBean;
    this.componentTypeToConstructFactory = componentTypeToConstructFactory;
  }

  public ConcurrentHashMap<Class<?>, Object> scan(final Properties configurations,
    final ConcurrentHashMap<Class<?>, Object> typeToBean) {

    this.typeToBean.putAll(typeToBean);
    return scan(configurations);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public ConcurrentHashMap<Class<?>, Object> scan(final Properties configurations) {

    if (MapUtils.isEmpty(configurations)) {
      throw new IllegalArgumentException("Argument configLocation can not be empty.");
    }
    try {
      ClassPath classPath = ClassPath.from(classLoader);
      String valueOfScanPackage = configurations.getProperty(SCAN_PACKAGE);
      if (isNotBlank(valueOfScanPackage)) {
        logger.debug("scan components for packages[{}]", valueOfScanPackage);
        String[] basePackages = valueOfScanPackage.split(INIT_PARAM_DELIMITERS);
        Set<ClassInfo> allClassInfo = new LinkedHashSet<ClassInfo>();
        for (String basePackage : basePackages) {
          allClassInfo.addAll(classPath.getTopLevelClassesRecursive(basePackage));
        }
        for (ClassInfo classInfo : allClassInfo) {
          Class<?> load = classInfo.load();
          for (Entry<Class<? extends Annotation>, DefaultConstructFactory> componentTypeWithConstructFactory : componentTypeToConstructFactory
            .entrySet()) {
            if (load.isAnnotationPresent(componentTypeWithConstructFactory.getKey())) {
              typeToBean.put(load, componentTypeWithConstructFactory.getValue().newInstance(load));
            }
          }
        }
        for (Entry<Class<?>, Object> classWithInstance : typeToBean.entrySet()) {
          Class<?> targetClass = classWithInstance.getKey();
          Object targetInstance = classWithInstance.getValue();
          for (Field field : targetClass.getDeclaredFields()) {
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
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (valueAnnotation != null) {
              logger.debug("field {}", field.getGenericType());
              if (Modifier.isFinal(field.getModifiers())) {
                continue;
              }
              field.setAccessible(true);
              Type genericType = field.getGenericType();
              Object value = configurations.getProperty(valueAnnotation.value()
                .replaceAll("[$#]+\\{", "").replaceAll("\\}", ""));
              if (value != null) {
                try {
                  String valueOfString = value.toString();
                  if (genericType == Integer.class || genericType == int.class) {
                    value = Integer.valueOf(valueOfString);
                  } else if (genericType == Long.class || genericType == long.class) {
                    value = Long.valueOf(valueOfString);
                  } else if (genericType == String.class) {
                    value = valueOfString;
                  } else if (genericType == Boolean.class || genericType == boolean.class) {
                    value = Boolean.valueOf(valueOfString);
                  } else if (genericType == Byte.class || genericType == byte.class) {
                    value = Byte.valueOf(valueOfString);
                  } else if (genericType == Short.class || genericType == short.class) {
                    value = Short.valueOf(valueOfString);
                  } else if (((Class) genericType).isEnum()) {
                    value = ValueUtils.getEnumInstance((Class) genericType, valueOfString);
                  }
                  if (value != null) {
                    field.set(targetInstance, value);
                  }
                } catch (IllegalAccessException e) {
                  logger.warn("Can not set {} to {}.{}", value,
                    targetInstance.getClass().getName(), field.getName(), e);
                }
              }
            }
          }
          for (Method method : targetClass.getDeclaredMethods()) {
            PostConstruct postConstruct = method.getAnnotation(PostConstruct.class);
            if (postConstruct != null) {
              method.setAccessible(true);
              method.invoke(targetInstance);
            }
          }
        }
      }
      return typeToBean;
    } catch (Exception e) {
      logger.error("Init application context failed.", e);
      throw new IllegalArgumentException();
    }
  }

  public ConcurrentHashMap<Class<? extends Annotation>, DefaultConstructFactory> getComponentTypeToConstructFactory() {

    return componentTypeToConstructFactory;
  }

  private final ClassLoader classLoader;

  private final ConcurrentHashMap<Class<?>, Object> typeToBean;

  private final ConcurrentHashMap<Class<? extends Annotation>, DefaultConstructFactory> componentTypeToConstructFactory;

  private static final String INIT_PARAM_DELIMITERS = "[,; \t\n]";

  private static final String SCAN_PACKAGE = "scan-package";

  private static final Logger logger = LoggerFactory.getLogger(ComponentScanner.class);
}
