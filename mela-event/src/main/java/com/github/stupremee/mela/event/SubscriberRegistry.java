package com.github.stupremee.mela.event;

import java.util.Set;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 08.07.19
 */
public interface SubscriberRegistry {

  /**
   * Registers the given {@code subscriber}.
   */
  void register(Subscriber subscriber);

  /**
   * Unregisters the given {@code subscriber}. Whenever a event was posted, it will not call the
   * given {@link Subscriber}.
   */
  void unregister(Subscriber subscriber);

  /**
   * Returns all registered {@link Subscriber Subscribers} that matches the given {@code event}.
   */
  Set<Subscriber> getSubscribersForEvent(Object event);

}
