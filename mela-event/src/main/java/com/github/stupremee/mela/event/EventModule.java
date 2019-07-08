package com.github.stupremee.mela.event;

import com.google.inject.AbstractModule;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 08.07.19
 */
public final class EventModule extends AbstractModule {

  private EventModule() {

  }

  @Override
  protected void configure() {
    super.configure();
  }

  public static AbstractModule create() {
    return new EventModule();
  }
}
