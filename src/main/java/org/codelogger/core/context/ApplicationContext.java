package org.codelogger.core.context;

import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

  @SuppressWarnings("unchecked")
  public <T> T getBean(final Class<T> type) {

    return (T) typeToBean.get(type);
  }

  protected ApplicationContext(final ConcurrentHashMap<Class<?>, Object> typeToBean) {

    this.typeToBean = typeToBean;
  }

  private final ConcurrentHashMap<Class<?>, Object> typeToBean;
}
