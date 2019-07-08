package com.github.stupremee.mela.event;

import java.util.Set;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 08.07.19
 */
public interface SubscriberRegistry {

  /**
   * Registers the given listener by finding all methods that are annotated with {@link
   * com.github.stupremee.mela.event.annotations.Subscribe}.
   */
  void register(Object listener);

  /**
   * Removes a listener. All methods in the given listener, which are annotated with {@link
   * com.github.stupremee.mela.event.annotations.Subscribe}  will not be called whenever an event is
   * called.
   */
  void unregister(Object listener);

  /**
   * Returns all registered {@link Subscriber Subscribers} that matches the given {@code event}.
   */
  Set<Subscriber> getSubscribersForEvent(Object event);

}
