package org.codelogger.core.context.bean;

import java.util.Properties;

public class DefaultConstructFactory {

  public DefaultConstructFactory(final Properties configurations) {

    super();
    this.configurations = configurations;
  }

  public <T> T newInstance(final Class<T> componentClass) throws InstantiationException,
    IllegalAccessException {

    return componentClass.newInstance();
  }

  protected Properties configurations;
}
