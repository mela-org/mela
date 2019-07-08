package com.github.stupremee.mela.event;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 08.07.19
 */
public interface Dispatcher {

  /**
   * This method will call {@link Subscriber#call(Object)} with the given {@code event} on all given
   * Subscribers.
   */
  void dispatchEvent(Object event, Iterable<Subscriber> subscribers);

}
