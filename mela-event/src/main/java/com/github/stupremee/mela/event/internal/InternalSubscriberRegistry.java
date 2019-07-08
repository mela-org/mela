package com.github.stupremee.mela.event.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.stupremee.mela.event.Subscriber;
import com.github.stupremee.mela.event.SubscriberRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 09.07.19
 */
public final class InternalSubscriberRegistry implements SubscriberRegistry {

  private final List<Subscriber> subscribers;

  private InternalSubscriberRegistry() {
    this.subscribers = new ArrayList<>();
  }

  @Override
  public void register(Subscriber subscriber) {
    checkNotNull(subscriber, "subscriber can't be null.");
    if (subscriber.getEventType().isPrimitive()) {
      throw new IllegalArgumentException("You can't register a primitive subscriber.");
    }
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
        .filter(subscriber -> subscriber.getEventType().isAssignableFrom(eventClass))
        .collect(Collectors.toUnmodifiableSet());
  }

  public static SubscriberRegistry create() {
    return new InternalSubscriberRegistry();
  }
}
