package com.github.stupremee.mela.event.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.stupremee.mela.event.listener.GenericListener;
import com.github.stupremee.mela.event.listener.Listener;
import com.github.stupremee.mela.event.subscriber.Subscriber;
import com.github.stupremee.mela.event.subscriber.SubscriberFactory;
import com.google.inject.Inject;
import java.util.function.Consumer;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 16.07.19
 */
public final class DefaultSubscriberFactory implements SubscriberFactory {

  @Inject
  DefaultSubscriberFactory() {

  }

  @Override
  public Subscriber fromListener(Object listener) {
    throw new AssertionError();
  }

  @Override
  public Subscriber fromListener(Listener listener) {
    checkNotNull(listener, "listener can't be null.");
    return Subscribers.fromListener(listener);
  }

  @Override
  public <T> Subscriber fromListener(GenericListener<T> listener) {
    checkNotNull(listener, "listener can't be null.");
    return Subscribers.fromGenericListener(listener);
  }

  @Override
  public <T> Subscriber fromCallback(Class<T> eventType, Consumer<T> callback) {
    checkNotNull(eventType, "eventType can't be null.");
    checkNotNull(callback, "callback can't be null.");
    return Subscribers.fromCallback(callback, eventType);
  }
}
