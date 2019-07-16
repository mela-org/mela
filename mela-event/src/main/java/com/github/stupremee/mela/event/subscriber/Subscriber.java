package com.github.stupremee.mela.event.subscriber;

import java.lang.reflect.Method;

/**
 * The subscriber interface represents a {@link Method} which get invoked by by calling {@link
 * Subscriber#call(Object)} with the given event and the eventType.
 *
 * @author Stu (https://github.com/Stupremee)
 * @since 08.07.19
 */
public interface Subscriber {

  /**
   * This method will invoke the {@link Method listener method} with the given {@code event} which
   * will be passed as argument.
   */
  void call(Object event);

  /**
   * Returns if this subscriber wants to receive events with the given type.
   */
  boolean supportsType(Class<?> type);
}
