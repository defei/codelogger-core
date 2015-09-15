package org.codelogger.core.context;

import java.lang.reflect.Type;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

  public <T> T getBean(final Type type) {

    @SuppressWarnings("unchecked")
    T bean = (T) typeToBean.get(type);
    if (bean == null && parent != null) {
      bean = parent.getBean(type);
    }
    return bean;
  }

  protected ApplicationContext(final ApplicationContext applicationContext,
    final Properties configurations) {

    typeToBean = applicationContext.typeToBean;
    this.configurations = configurations;
  }

  protected ApplicationContext(final ConcurrentHashMap<Class<?>, Object> typeToBean,
    final Properties configurations) {

    this.typeToBean = typeToBean;
    this.configurations = configurations;
  }

  protected ApplicationContext(final ApplicationContext applicationContext,
    final ConcurrentHashMap<Class<?>, Object> typeToBean, final Properties configurations) {

    this(typeToBean, configurations);
    parent = applicationContext;
  }

  public ApplicationContext getParent() {

    return parent;
  }

  public Properties getConfigurations() {

    return configurations;
  }

  private final Properties configurations;

  protected final ConcurrentHashMap<Class<?>, Object> typeToBean;

  private ApplicationContext parent;

}
