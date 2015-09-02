package org.codelogger.core.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.codelogger.test.beans.TestServiceC;
import org.junit.Test;

public class ApplicationContextLoaderTest {

  @Test
  public void initApplicationContext() {

    ClassLoader classLoader = this.getClass().getClassLoader();
    ApplicationContextLoader applicationContextLoader = ApplicationContextLoader.from(classLoader);
    ApplicationContext applicationContextParent = applicationContextLoader
      .initApplicationContext("codelogger.config");
    ApplicationContextLoader applicationContextLoader2 = new ApplicationContextLoader(
      applicationContextParent, classLoader);
    ApplicationContext applicationContext = applicationContextLoader2
      .initApplicationContext("codelogger1.config");
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
