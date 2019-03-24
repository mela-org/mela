package com.github.stupremee.mela.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 23.03.2019
 */
public final class Loggers {

  private Loggers() {
  }

  /**
   * Create a logger of the calling class by using {@link LoggerFactory#getLogger(Class)}.
   *
   * @return the created logger
   */
  public static Logger logger() {
    return LoggerFactory.getLogger(
        StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass());
  }

  /**
   * Create a logger of a given name by using {@link LoggerFactory#getLogger(String)}.
   *
   * @param name the logger name
   * @return the created logger
   */
  public static Logger ofName(String name) {
    return LoggerFactory.getLogger(name);
  }

  /**
   * Create a logger of the given clazz name.
   *
   * @param clazz the clazz
   * @return the created logger
   */
  public static Logger ofClassName(Class clazz) {
    return LoggerFactory.getLogger(
        clazz.getCanonicalName() == null ? clazz.getName() : clazz.getCanonicalName());
  }

  /**
   * Create a logger of the calling clazz name by using {@link Loggers#ofClassName(Class)}.
   *
   * @return the created logger
   */
  public static Logger ofClassName() {
    Class clazz =
        StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
    return ofClassName(clazz);
  }

}
