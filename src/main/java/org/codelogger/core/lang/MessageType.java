package org.codelogger.core.lang;

public enum MessageType {

  INFO("info"), SUCCESS("success"), WARNING("warning"), DANGER("danger"), ERROR("error");

  private MessageType(String value) {

    this.value = value;
  }

  private final String value;

  public String getValue() {

    return value;
  }

}
