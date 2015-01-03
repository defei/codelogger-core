package org.codelogger.core.bean;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Date;
import java.util.List;

public class Request {

  public Object key;

  private List<Date> requestTimes = newArrayList();

  public Object getKey() {

    return key;
  }

  public void setKey(final Object key) {

    this.key = key;
  }

  public List<Date> getRequestTimes() {

    return requestTimes;
  }

  public void setRequestTimes(final List<Date> requestTimes) {

    this.requestTimes = requestTimes;
  }

}
