package org.codelogger.core.exception;

public class ResourceNotFoundExcception extends RuntimeException {

  private static final long serialVersionUID = -1830419769126285148L;

  public ResourceNotFoundExcception(final String message, final Throwable cause) {

    super(message, cause);
  }

  public ResourceNotFoundExcception(final String message) {

    super(message);
  }

  public ResourceNotFoundExcception(final Throwable cause) {

    super(cause);
  }

}
