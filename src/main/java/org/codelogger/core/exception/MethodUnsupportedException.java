package org.codelogger.core.exception;

public class MethodUnsupportedException extends Exception {

  private static final long serialVersionUID = -1711108501032694021L;

  public MethodUnsupportedException() {

    super("This method is unsupported currently.");
  }

  public MethodUnsupportedException(final String message, final Throwable cause) {

    super(message, cause);
  }

  public MethodUnsupportedException(final String message) {

    super(message);
  }

  public MethodUnsupportedException(final Throwable cause) {

    super(cause);
  }

}
