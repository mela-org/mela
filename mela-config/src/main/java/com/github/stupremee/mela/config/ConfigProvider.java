package com.github.stupremee.mela.config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 18.06.19
 */
public interface ConfigProvider {

  /**
   * Loads a config from the given {@link String} by mapping the config to the given type.
   *
   * @return The config as the given type
   */
  default <T> T parse(String content, Class<T> configType) {
    InputStream input = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    return parse(input, configType);
  }

  /**
   * Loads a config from the given {@link Path} by mapping the config to the given type.
   *
   * @return The config as the given type
   */
  default <T> T parse(Path path, Class<T> configType) {
    return parse(path.toFile(), configType);
  }

  /**
   * Loads a config from the given {@link File} by mapping the config to the given type.
   *
   * @return The config as the given type
   */
  default <T> T parse(File file, Class<T> configType) {
    try (InputStream input = new FileInputStream(file)) {
      return parse(input, configType);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Loads a config from the given {@link File} by mapping the config to the given type.
   *
   * @return The config as the given type
   */
  <T> T parse(InputStream input, Class<T> configType);

  /**
   * Loads a config from the given {@link Path}.
   *
   * @return The {@link Config}
   */
  default Config load(String content) {
    InputStream input = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    return load(input);
  }

  /**
   * Loads a config from the given {@link Path}.
   *
   * @return The {@link Config}
   */
  default Config load(Path path) {
    return load(path.toFile());
  }

  /**
   * Loads a config from the given {@link File}.
   *
   * @return The {@link Config}
   */
  default Config load(File file) {
    try (InputStream input = new FileInputStream(file)) {
      return load(input);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Loads a config from the given {@link Path}.
   *
   * @return The {@link Config}
   */
  Config load(InputStream input);
}