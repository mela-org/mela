package com.github.stupremee.mela.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.stupremee.mela.config.json.JsonConfigProvider;
import com.github.stupremee.mela.config.yaml.YamlConfigProvider;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.io.InputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 09.07.19
 */
final class CustomConfigTest {

  @Inject
  private Config config;
  @Inject
  @MyCustomConfig
  private Config customConfig;

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
    Injector injector = Guice.createInjector(module);
    injector.injectMembers(this);
  }

  @AfterEach
  void tearDown() {
    config = null;
    customConfig = null;
  }

  @Test
  void configTests() {
    assertThat(customConfig.getString("some"))
        .contains("hello");
    assertThat(config.getString("some"))
        .contains("hallo");
  }
}
