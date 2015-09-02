package org.codelogger.core.context;

import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

  public <T> T getBean(final Type type) {

    @SuppressWarnings("unchecked")
    T object = (T) typeToBean.get(type);
    if (object == null && parent != null) {
      object = parent.getBean(type);
    }
    return object;
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

  private ApplicationContext parent;

  private final ConcurrentHashMap<Class<?>, Object> typeToBean;

}
