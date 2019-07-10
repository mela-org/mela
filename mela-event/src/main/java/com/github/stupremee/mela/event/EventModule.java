package com.github.stupremee.mela.event;

import com.github.stupremee.mela.event.dispatchers.Dispatchers;
import com.github.stupremee.mela.event.internal.DefaultSubscriberRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 08.07.19
 */
public final class EventModule extends AbstractModule {

  private EventModule() {

  }

  @Override
  protected void configure() {
    bind(SubscriberRegistry.class)
        .toProvider(DefaultSubscriberRegistry::create)
        .in(Singleton.class);

    bind(Dispatcher.class)
        .toProvider(Dispatchers::perThreadQueued);
  }

  /**
   * Creates a new {@link EventModule} with a Per Thread Queued Dispatcher.
   */
  public static AbstractModule create() {
    return new EventModule();
  }
}
