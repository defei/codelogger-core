package org.codelogger.core.utils;

import net.ftng.commons.lang.utils.HttpUtils;

import java.util.Map;

public class HttpDownloader {

  public String doGet(final String url) {

    return HttpUtils.doGet(url);
  }

  public String doGet(final String url, final int retryTimes) {

    return HttpUtils.doGet(url, retryTimes);
  }

  public String doPost(final String action, final Map<String, String> parameters) {

    return HttpUtils.doPost(action, parameters);
  }

  public String doPost(final String action, final Map<String, String> parameters,
      final int retryTimes) {

    return HttpUtils.doPost(action, parameters, retryTimes);
  }
}
