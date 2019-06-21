package com.github.stupremee.mela.config.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 19.06.19
 */
public final class JsonConfigModule extends AbstractModule {

  private JsonConfigModule() {
  }

  @Override
  protected void configure() {
    bind(ObjectMapper.class)
        .in(Singleton.class);
  }

  public static AbstractModule create() {
    return new JsonConfigModule();
  }
}
