package com.github.stupremee.mela.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stupremee.mela.config.annotations.ConfigMapper;
import com.github.stupremee.mela.config.annotations.ConfigSource;
import com.github.stupremee.mela.config.jackson.JacksonConfigAssembler;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import java.io.InputStream;

/**
 * @author Stu <https://github.com/Stupremee>
 * @since 19.06.19
 */
@SuppressWarnings({"unused"})
public final class ConfigModule extends AbstractModule {

  private final ConfigProvider configProvider;

  private ConfigModule(ConfigProvider configProvider) {
    this.configProvider = configProvider;
  }

  @Override
  protected void configure() {
    bind(ObjectMapper.class)
        .annotatedWith(ConfigMapper.class)
        .toProvider(() -> new ObjectMapper(configProvider.getJsonFactory()))
        .in(Singleton.class);

    bind(InputStream.class)
        .annotatedWith(ConfigSource.class)
        .toProvider(configProvider::getSource)
        .in(Singleton.class);

    bind(Config.class)
        .toProvider(JacksonConfigAssembler.class)
        .asEagerSingleton();
  }

  public static AbstractModule of(ConfigProvider configProvider) {
    return new ConfigModule(configProvider);
  }
}