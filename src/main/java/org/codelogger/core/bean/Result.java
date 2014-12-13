package org.codelogger.core.bean;

import org.codelogger.core.lang.MessageType;

public class Result<D> {

  private MessageType messageType;

  private D data;

  public Result(MessageType messageType, D data) {

    super();
    this.messageType = messageType;
    this.data = data;
  }

  public static <D> Result<D> newResult(MessageType messageType, D data) {

    return new Result<D>(messageType, data);
  }

  public MessageType getMessageType() {

    return messageType;
  }

  public D getData() {

    return data;
  }

}
