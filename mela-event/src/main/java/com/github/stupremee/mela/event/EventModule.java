package com.github.stupremee.mela.event;

import com.github.stupremee.mela.event.dispatchers.Dispatchers;
import com.github.stupremee.mela.event.internal.DefaultEventBus;
import com.github.stupremee.mela.event.internal.DefaultSubscriberRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
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

    bind(EventBus.class)
        .to(DefaultEventBus.class);

    bind(Dispatcher.class)
        .toProvider(Dispatchers::immediate);
  }

  /**
   * Creates a new {@link EventModule} with a Immediate Dispatcher.
   */
  public static Module create() {
    return new EventModule();
  }
}
