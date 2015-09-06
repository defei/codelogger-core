package org.codelogger.core.context.exception;

public class ContextInitException extends Error {

  private static final long serialVersionUID = -953956012029057338L;

  public ContextInitException() {

    super();
  }

  public ContextInitException(final String message, final Throwable cause,
    final boolean enableSuppression, final boolean writableStackTrace) {

    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ContextInitException(final String message, final Throwable cause) {

    super(message, cause);
  }

  public ContextInitException(final String message) {

    super(message);
  }

  public ContextInitException(final Throwable cause) {

    super(cause);
  }

}
