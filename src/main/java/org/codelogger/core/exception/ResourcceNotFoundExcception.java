package org.codelogger.core.exception;

public class ResourcceNotFoundExcception extends RuntimeException {

  private static final long serialVersionUID = -1830419769126285148L;

  public ResourcceNotFoundExcception(final String message, final Throwable cause) {

    super(message, cause);
  }

  public ResourcceNotFoundExcception(final String message) {

    super(message);
  }

  public ResourcceNotFoundExcception(final Throwable cause) {

    super(cause);
  }

}
