package org.codelogger.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {

  private final static Logger log = LoggerFactory.getLogger(JsonUtils.class);

  public static String toJson(final Object obj) {

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      return "";
    }
  }

  public static <T> T fromJson(final String json, final Class<T> valueType) {

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    try {
      return objectMapper.readValue(json, valueType);
    } catch (Exception e) {
      log.debug("parse json data'{}' to '{}' failed.", json, valueType.getName());
      return null;
    }
  }
}
