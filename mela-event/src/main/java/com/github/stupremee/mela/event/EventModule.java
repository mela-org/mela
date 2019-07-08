package com.github.stupremee.mela.event;

import com.github.stupremee.mela.event.internal.InternalSubscriberRegistry;
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
        .toProvider(InternalSubscriberRegistry::create)
        .in(Singleton.class);
  }

  public static AbstractModule create() {
    return new EventModule();
  }
}
