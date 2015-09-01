package org.codelogger.core.context;

import org.codelogger.core.context.stereotype.Autowired;
import org.codelogger.core.context.stereotype.Service;
import org.codelogger.test.beans.TestServiceC;

@Service
public class TestServiceA {

  @Autowired
  private TestServiceB service;

  private TestServiceC serviceC;

  public TestServiceB getService() {

    return service;
  }

  public TestServiceC getServiceC() {

    return serviceC;
  }

}
