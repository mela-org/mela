package com.github.stupremee.mela.event.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.stupremee.mela.event.Dispatcher;
import com.github.stupremee.mela.event.EventBus;
import com.github.stupremee.mela.event.annotations.EventExecutor;
import com.github.stupremee.mela.event.subscriber.Subscriber;
import com.github.stupremee.mela.event.subscriber.SubscriberFactory;
import com.github.stupremee.mela.event.subscriber.SubscriberRegistry;
import com.google.inject.Inject;
import java.util.concurrent.Executor;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 11.07.19
 */
public final class DefaultEventBus implements EventBus {

  private final SubscriberRegistry subscriberRegistry;
  private final Dispatcher dispatcher;
  private final Executor executor;
  private final SubscriberFactory subscriberFactory;

  @Inject
  DefaultEventBus(
      SubscriberRegistry subscriberRegistry,
      Dispatcher dispatcher,
      @EventExecutor Executor executor,
      SubscriberFactory subscriberFactory) {
    this.subscriberRegistry = subscriberRegistry;
    this.dispatcher = dispatcher;
    this.executor = executor;
    this.subscriberFactory = subscriberFactory;
  }

  @Override
  public void post(Object event) {
    checkNotNull(event, "event can't be null.");
    executor.execute(
        () -> dispatcher.dispatchEvent(event, subscriberRegistry.getSubscribersForEvent(event)));
  }

  @Override
  public void register(Subscriber subscriber) {
    checkNotNull(subscriber, "subscriber can't be null.");
    throw new AssertionError();
    // TODO: 11.07.19 Implement this
  }

  @Override
  public boolean unregister(Subscriber subscriber) {
    checkNotNull(subscriber, "subscriber can't be null.");
    throw new AssertionError();
    // TODO: 11.07.19 Implement this
  }

  @Override
  public void registerFromClasspath() {
    throw new AssertionError();
    // TODO: 11.07.19 Implement this
  }

  @Override
  public SubscriberFactory getSubscriberFactory() {
    return subscriberFactory;
  }
}
