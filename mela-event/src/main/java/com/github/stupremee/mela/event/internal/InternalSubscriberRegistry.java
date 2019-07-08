package com.github.stupremee.mela.event.internal;

import com.github.stupremee.mela.event.Subscriber;
import com.github.stupremee.mela.event.SubscriberRegistry;
import java.util.Set;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 09.07.19
 */
public final class InternalSubscriberRegistry implements SubscriberRegistry {

  private InternalSubscriberRegistry() {

  }

  @Override
  public void register(Subscriber subscriber) {

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
