package org.codelogger.test.beans;

import org.codelogger.core.context.TestServiceA;
import org.codelogger.core.context.TestServiceB;
import org.codelogger.core.context.stereotype.Autowired;
import org.codelogger.core.context.stereotype.Service;

@Service
public class TestServiceC {

  @Autowired
  private TestServiceA serviceA;

  @Autowired
  private TestServiceB serviceB;

  public TestServiceA getServiceA() {

    return serviceA;
  }

  public TestServiceB getServiceB() {

    return serviceB;
  }

}
