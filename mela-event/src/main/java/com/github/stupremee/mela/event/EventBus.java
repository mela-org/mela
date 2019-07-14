package com.github.stupremee.mela.event;

import com.github.stupremee.mela.event.annotations.AutoSubscriber;
import com.github.stupremee.mela.event.subscriber.Subscriber;
import com.github.stupremee.mela.event.subscriber.SubscriberRegistry;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 08.07.19
 */
public interface EventBus {

  /**
   * Posts a new event to all registered {@link Subscriber Subscribers} by finding all Subscribers
   * via the {@link SubscriberRegistry} and calling them via the {@link Dispatcher}.
   *
   * @see Dispatcher#dispatchEvent(Object, Iterable)
   * @see SubscriberRegistry#getSubscribersForEvent(Object)
   */
  void post(Object event);

  /**
   * Registers the given {@code listener} by finding all methods which are annotated with {@link
   * com.github.stupremee.mela.event.annotations.Subscribe}.
   *
   * @throws IllegalArgumentException If the given {@link Subscriber} already is registered or
   *     the {@link Subscriber} has a primitive type
   * @see SubscriberRegistry#register(Subscriber)
   */
  void register(Object listener);

  /**
   * Registers all Classes that are annotated with {@link AutoSubscriber} by creating an instance
   * via Guice.
   */
  void registerFromClasspath();

  /**
   * Removes a listener. All methods in the given listener, which are annotated with {@link
   * com.github.stupremee.mela.event.annotations.Subscribe}  will not be called whenever an event is
   * called.
   *
   * @see SubscriberRegistry#unregister(Subscriber)
   */
  boolean unregister(Object listener);
}
