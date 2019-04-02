package com.github.stupremee.mela.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 26.03.2019
 */
public final class Loggers {

  private Loggers() {
  }

  /**
   * Create a getLogger of the calling class by using {@link LoggerFactory#getLogger(Class)}.
   *
   * @return the created getLogger
   */
  public static Logger getLogger() {
    return LoggerFactory.getLogger(
        StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass());
  }

  /**
   * Create a getLogger of the calling class by using {@link LoggerFactory#getLogger(Class)}.
   *
   * @return the created getLogger
   */
  public static Logger getLogger(Class<?> clazz) {
    return LoggerFactory.getLogger(clazz);
  }

  /**
   * Create a getLogger of a given name by using {@link LoggerFactory#getLogger(String)}.
   *
   * @param name the getLogger name
   * @return the created getLogger
   */
  public static Logger getLogger(String name) {
    return LoggerFactory.getLogger(name);
  }
}
