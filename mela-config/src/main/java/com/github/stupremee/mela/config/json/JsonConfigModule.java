package com.github.stupremee.mela.config.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stupremee.mela.config.Config;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 19.06.19
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class JsonConfigModule extends AbstractModule {

  private final ObjectMapper mapper;
  private final InputStream config;

  private JsonConfigModule(InputStream config) {
    this.mapper = new ObjectMapper();
    this.config = config;
  }

  @Override
  protected void configure() {
    bind(ObjectMapper.class)
        .toInstance(mapper);

    bind(Config.class)
        .toProvider(JsonConfigProvider.class)
        .asEagerSingleton();
  }

  @Provides
  @Singleton
  private JsonConfigProvider provideJsonConfigProvider() {
    return new JsonConfigProvider(mapper, config);
  }

  /**
   * Creates a new {@link JsonConfigModule} which will read the {@link Config} from the given {@link
   * InputStream}.
   */
  public static AbstractModule create(InputStream config) {
    return new JsonConfigModule(config);
  }

  /**
   * Creates a new {@link JsonConfigModule} which will read the {@link Config} from the given {@link
   * File}.
   */
  public static AbstractModule fromFile(File configFile) {
    try {
      return create(new FileInputStream(configFile));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates a new {@link JsonConfigModule} which will read the {@link Config} from the given {@link
   * Path}.
   */
  public static AbstractModule fromPath(Path configPath) {
    return fromFile(configPath.toFile());
  }

  /**
   * Creates a new {@link JsonConfigModule} which will read the {@link Config} from the given {@link
   * String content}.
   */
  public static AbstractModule fromContent(String content) {
    InputStream config = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    return create(config);
  }
}
