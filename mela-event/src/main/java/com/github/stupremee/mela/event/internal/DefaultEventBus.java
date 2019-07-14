package com.github.stupremee.mela.event.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.stupremee.mela.event.Dispatcher;
import com.github.stupremee.mela.event.EventBus;
import com.github.stupremee.mela.event.subscriber.SubscriberRegistry;
import com.github.stupremee.mela.event.annotations.EventExecutor;
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

  @Inject
  DefaultEventBus(SubscriberRegistry subscriberRegistry, Dispatcher dispatcher,
      @EventExecutor Executor executor) {
    this.subscriberRegistry = subscriberRegistry;
    this.dispatcher = dispatcher;
    this.executor = executor;
  }

  @Override
  public void post(Object event) {
    checkNotNull(event, "event can't be null.");
    executor.execute(
        () -> dispatcher.dispatchEvent(event, subscriberRegistry.getSubscribersForEvent(event)));
  }

  @Override
  public void register(Object listener) {
    checkNotNull(listener, "listener can't be null.");
    throw new AssertionError();
    // TODO: 11.07.19 Implement this
  }

  @Override
  public boolean unregister(Object listener) {
    checkNotNull(listener, "listener can't be null.");
    throw new AssertionError();
    // TODO: 11.07.19 Implement this
  }

  @Override
  public void registerFromClasspath() {
    throw new AssertionError();
    // TODO: 11.07.19 Implement this
  }
}
