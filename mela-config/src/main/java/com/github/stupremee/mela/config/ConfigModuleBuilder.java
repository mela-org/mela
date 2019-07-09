package com.github.stupremee.mela.config;

import com.google.inject.Module;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 09.07.19
 */
public final class ConfigModuleBuilder {

  private final List<CustomConfig> customConfigs;
  private ConfigProvider rootConfigProvider;

  ConfigModuleBuilder() {
    this.customConfigs = new ArrayList<>();
  }

  public CustomConfigBuilder customConfig() {
    return new CustomConfigBuilder(this);
  }

  public ConfigModuleBuilder rootProvider(ConfigProvider configProvider) {
    this.rootConfigProvider = configProvider;
    return this;
  }

  public Module build() {
    if (rootConfigProvider == null) {
      throw new IllegalArgumentException("path and rootConfigProvider must be set.");
    }
    return ConfigModule.fromBuilder(this);
  }

  ConfigProvider getRootConfigProvider() {
    return rootConfigProvider;
  }

  List<CustomConfig> getCustomConfigs() {
    return customConfigs;
  }

  static final class CustomConfig {

    private final ConfigProvider provider;
    private final Class<? extends Annotation> annotation;

    private CustomConfig(ConfigProvider provider, Class<? extends Annotation> annotation) {
      this.provider = provider;
      this.annotation = annotation;
    }

    Class<? extends Annotation> getAnnotation() {
      return annotation;
    }

    ConfigProvider getProvider() {
      return provider;
    }
  }

  public static final class CustomConfigBuilder {

    private final ConfigModuleBuilder configModuleBuilder;
    private Class<? extends Annotation> annotation;
    private ConfigProvider provider;

    private CustomConfigBuilder(ConfigModuleBuilder configModuleBuilder) {
      this.configModuleBuilder = configModuleBuilder;
    }

    public CustomConfigBuilder annotatedWith(Class<? extends Annotation> annotation) {
      this.annotation = annotation;
      return this;
    }

    public CustomConfigBuilder providedBy(ConfigProvider provider) {
      this.provider = provider;
      return this;
    }

    public ConfigModuleBuilder register() {
      if (annotation == null || provider == null) {
        throw new IllegalArgumentException("annotation and provider must be set.");
      }

      configModuleBuilder.customConfigs.add(new CustomConfig(provider, annotation));
      return configModuleBuilder;
    }
  }

}
