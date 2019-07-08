package com.github.stupremee.mela.event.internal;

import com.github.stupremee.mela.event.Subscriber;
import com.github.stupremee.mela.event.SubscriberRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    if (subscribers.contains(subscriber)) {
      throw new IllegalArgumentException(
          "The Subscriber " + subscriber + " is already registered.");
    }
    subscribers.add(subscriber);
  }

  @Override
  public boolean unregister(Subscriber subscriber) {
    return false;
  }

  @Override
  public Set<Subscriber> getSubscribersForEvent(Object event) {
    return null;
  }

  public static SubscriberRegistry create() {
    return new InternalSubscriberRegistry();
  }
}
