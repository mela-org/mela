package com.github.stupremee.mela.event.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.stupremee.mela.event.subscriber.Subscriber;
import com.github.stupremee.mela.event.subscriber.SubscriberRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 09.07.19
 */
public final class DefaultSubscriberRegistry implements SubscriberRegistry {

  private final List<Subscriber> subscribers;

  private DefaultSubscriberRegistry() {
    this.subscribers = new ArrayList<>();
  }

  @Override
  public void register(Subscriber subscriber) {
    checkNotNull(subscriber, "subscriber can't be null.");
    if (subscribers.contains(subscriber)) {
      throw new IllegalArgumentException(
          "The Subscriber " + subscriber + " is already registered.");
    }
    subscribers.add(subscriber);
  }

  @Override
  public boolean unregister(Subscriber subscriber) {
    checkNotNull(subscriber, "subscriber can't be null.");
    return subscribers.remove(subscriber);
  }

  @Override
  public Set<Subscriber> getSubscribersForEvent(Object event) {
    checkNotNull(event, "event can't be null.");
    Class<?> eventClass = event.getClass();
    return subscribers.stream()
        .filter(subscriber -> subscriber.supportsType(eventClass))
        .collect(Collectors.toUnmodifiableSet());
  }

  @Override
  public void registerFromClasspath() {

  }


  /**
   * Creates a new {@link SubscriberRegistry}.
   */
  public static SubscriberRegistry create() {
    return new DefaultSubscriberRegistry();
  }
}
