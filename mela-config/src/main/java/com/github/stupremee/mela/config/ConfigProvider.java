package com.github.stupremee.mela.config;

import java.io.File;
import java.nio.file.Path;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 18.06.19
 */
public interface ConfigProvider {

  /**
   * Loads a config from the given {@link File} by mapping the config to the given type.
   *
   * @return The config as the given type
   */
  <T> T load(File file, Class<T> configType);

  /**
   * Loads a config from the given {@link Path} by mapping the config to the given type.
   *
   * @return The config as the given type
   */
  <T> T load(Path path, Class<T> configType);
}