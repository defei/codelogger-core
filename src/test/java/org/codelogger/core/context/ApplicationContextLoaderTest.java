package org.codelogger.core.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.codelogger.test.beans.TestServiceC;
import org.junit.Test;

public class ApplicationContextLoaderTest {

  @Test
  public void initApplicationContext() {

    ApplicationContextLoader applicationContextLoader = new ApplicationContextLoader();
    ApplicationContext applicationContext = applicationContextLoader
      .initApplicationContext("codelogger.config");
    TestServiceA testServiceA = applicationContext.getBean(TestServiceA.class);
    TestServiceB testServiceB = applicationContext.getBean(TestServiceB.class);
    assertNotNull(testServiceA);
    assertNotNull(testServiceB);
    assertNull(testServiceA.getServiceC());
    assertEquals(testServiceA, testServiceB.getService());
    assertEquals(testServiceB, testServiceA.getService());
    TestServiceC testServiceC = applicationContext.getBean(TestServiceC.class);
    assertNotNull(testServiceC);
    assertEquals(testServiceA, testServiceC.getServiceA());
    assertEquals(testServiceB, testServiceC.getServiceB());
  }
}
