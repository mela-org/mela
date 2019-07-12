package com.github.stupremee.mela.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.stupremee.mela.config.annotations.ConfigValue;
import com.github.stupremee.mela.config.json.JsonConfigProvider;
import com.github.stupremee.mela.config.yaml.YamlConfigProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 12.07.19
 */
final class InjectConfigValueTest {

  private Injector injector;

  @ConfigValue("some")
  private String rootString;

  @ConfigValue("some")
  @MyCustomConfig
  private String customString;

  @BeforeEach
  void setUp() {
    InputStream customConfig = CustomConfigTest.class.getResourceAsStream("/custom.yaml");
    InputStream rootConfig = CustomConfigTest.class.getResourceAsStream("/root.json");

    // @formatter:off
    Module module = ConfigModule.builder()
        .rootProvider(JsonConfigProvider.create(rootConfig))
        .customConfig()
          .annotatedWith(MyCustomConfig.class)
          .providedBy(YamlConfigProvider.create(customConfig))
          .register()
        .build();
    // @formatter:on
    this.injector = Guice.createInjector(module);
  }

  @Test
  void testConfigValueInjections() {
    injector.injectMembers(this);

    assertThat(rootString).isEqualTo("hallo");
    assertThat(customString).isEqualTo("hello");
  }

}
