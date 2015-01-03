package org.codelogger.core.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.codelogger.core.test.TestBase;
import org.junit.Test;

public class RequestLoggerTest extends TestBase {

  private RequestLogger requestLogger = new RequestLogger(1000, 5L, TimeUnit.MINUTES);

  private String key = "hello";

  @Test
  public void isLatestRequestTimeOutof() throws InterruptedException {

    assertTrue(requestLogger.isLatestRequestTimeOutof(key, TimeUnit.SECONDS, 5));
    Thread.sleep(2000);
    assertFalse(requestLogger.isLatestRequestTimeOutof(key, TimeUnit.SECONDS, 5));
    Thread.sleep(7000);
    assertTrue(requestLogger.isLatestRequestTimeOutof(key, TimeUnit.SECONDS, 5));
  }
}
