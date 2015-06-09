package org.codelogger.core.test;

import org.mockito.Matchers;
import org.mockito.Mockito;

public class TestBase {

  protected Long defaultId = 1L;

  protected <T> T eq(final T t) {

    return Mockito.eq(t);
  }

  protected <T> T any(final Class<T> clazz) {

    return Matchers.any(clazz);
  }

  protected String anyString() {

    return Matchers.anyString();
  }

  protected Long anyLong() {

    return Matchers.anyLong();
  }

  protected Double anyDouble() {

    return Matchers.anyDouble();
  }
}
