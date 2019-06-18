package com.github.stupremee.mela.config.exceptions;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 18.06.19
 */
public final class InvalidTypeException extends RuntimeException {

  private InvalidTypeException() {
  }

  private InvalidTypeException(String message) {
    super(message);
  }

  private InvalidTypeException(String message, Throwable cause) {
    super(message, cause);
  }

  private InvalidTypeException(Throwable cause) {
    super(cause);
  }

  public static RuntimeException empty() {
    return new InvalidTypeException();
  }

  public static RuntimeException withMessage(String message) {
    return new InvalidTypeException(message);
  }

  public static RuntimeException withCause(Throwable cause) {
    return new InvalidTypeException(cause);
  }
}
