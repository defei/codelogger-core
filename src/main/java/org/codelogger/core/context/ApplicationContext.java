package org.codelogger.core.context;

import java.lang.reflect.Type;
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

  protected ApplicationContext(final ApplicationContext applicationContext) {

    typeToBean = applicationContext.typeToBean;
  }

  protected ApplicationContext(final ConcurrentHashMap<Class<?>, Object> typeToBean) {

    this.typeToBean = typeToBean;
  }

  protected ApplicationContext(final ApplicationContext applicationContext,
    final ConcurrentHashMap<Class<?>, Object> typeToBean) {

    this(typeToBean);
    parent = applicationContext;
  }

  public ApplicationContext getParent() {

    return parent;
  }

  protected final ConcurrentHashMap<Class<?>, Object> typeToBean;

  private ApplicationContext parent;

}
