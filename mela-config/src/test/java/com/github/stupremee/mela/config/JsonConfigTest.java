package com.github.stupremee.mela.config;

import com.github.stupremee.mela.config.json.JsonConfigProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.io.InputStream;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 22.06.19
 */
class JsonConfigTest extends BasicConfigTest {

  private static final Config config;

  static {
    InputStream stream = JsonConfigTest.class.getResourceAsStream("/config.json");
    Module configModule = ConfigModule.of(JsonConfigProvider.create(stream));
    Injector injector = Guice.createInjector(configModule);
    config = injector.getInstance(Config.class);
  }

  private JsonConfigTest() {
    super(config);
  }
}
