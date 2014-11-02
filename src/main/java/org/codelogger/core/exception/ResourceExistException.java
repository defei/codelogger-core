package org.codelogger.core.exception;

public class ResourceExistException extends RuntimeException {

  private static final long serialVersionUID = 6845095838791719152L;

  public ResourceExistException(final String message, final Throwable cause) {

    super(message, cause);
  }

  public ResourceExistException(final String message) {

    super(message);
  }

  public ResourceExistException(final Throwable cause) {

    super(cause);
  }

}
