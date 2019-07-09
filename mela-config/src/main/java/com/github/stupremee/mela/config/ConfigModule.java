package com.github.stupremee.mela.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stupremee.mela.config.ConfigModuleBuilder.CustomConfig;
import com.github.stupremee.mela.config.annotations.ConfigMapper;
import com.github.stupremee.mela.config.annotations.ConfigSource;
import com.github.stupremee.mela.config.jackson.JacksonConfigAssembler;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 19.06.19
 */
@SuppressWarnings({"unused"})
public final class ConfigModule extends AbstractModule {

  private final List<CustomConfig> customConfigs;
  private final ConfigProvider rootConfigProvider;

  private ConfigModule(ConfigModuleBuilder builder) {
    this.rootConfigProvider = builder.getRootConfigProvider();
    this.customConfigs = builder.getCustomConfigs();
  }

  private ConfigModule(ConfigProvider configProvider) {
    this.rootConfigProvider = configProvider;
    this.customConfigs = Collections.emptyList();
  }

  @Override
  protected void configure() {
    bind(ObjectMapper.class)
        .annotatedWith(ConfigMapper.class)
        .toProvider(() -> new ObjectMapper(rootConfigProvider.getJsonFactory()))
        .in(Singleton.class);

    bind(InputStream.class)
        .annotatedWith(ConfigSource.class)
        .toProvider(rootConfigProvider::getSource)
        .in(Singleton.class);

    bind(Config.class)
        .toProvider(JacksonConfigAssembler.class)
        .asEagerSingleton();

    for (CustomConfig config : this.customConfigs) {
      ObjectMapper mapper = new ObjectMapper(config.getProvider().getJsonFactory());
      bind(Config.class)
          .annotatedWith(config.getAnnotation())
          .toProvider(JacksonConfigAssembler.create(mapper, config.getProvider().getSource()))
          .asEagerSingleton();
    }
  }

  static Module fromBuilder(ConfigModuleBuilder builder) {
    return new ConfigModule(builder);
  }

  /**
   * Creates a new {@link ConfigModule} with only the root config which is mapped to the {@link
   * Config Config interface}.
   */
  public static Module of(ConfigProvider configProvider) {
    return new ConfigModule(configProvider);
  }

  /**
   * Creates a new {@link ConfigModuleBuilder} that can be used to register custom configs.
   */
  public static ConfigModuleBuilder builder() {
    return new ConfigModuleBuilder();
  }
}