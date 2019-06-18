package com.github.stupremee.mela.config.exceptions;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 18.06.19
 */
public final class ConfigLoadException extends RuntimeException {

  private ConfigLoadException() {
  }

  private ConfigLoadException(String message) {
    super(message);
  }

  private ConfigLoadException(String message, Throwable cause) {
    super(message, cause);
  }

  private ConfigLoadException(Throwable cause) {
    super(cause);
  }

  public static RuntimeException empty() {
    return new ConfigLoadException();
  }

  public static RuntimeException withMessage(String message) {
    return new ConfigLoadException(message);
  }

  public static RuntimeException withCause(Throwable cause) {
    return new ConfigLoadException(cause);
  }
}
