package org.codelogger.core.context;

import org.codelogger.core.context.stereotype.Autowired;
import org.codelogger.core.context.stereotype.Service;

@Service
public class TestServiceB {

  @Autowired
  private TestServiceA service;

  public TestServiceA getService() {

    return service;
  }

}
