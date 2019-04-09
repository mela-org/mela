package com.github.stupremee.mela.configuration;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 28.03.2019
 */
public interface ConfigurationParser {

  /**
   * Returns a {@link ConfigurationParser} that parses {@link Configuration configs} to hocon
   * format.
   *
   * @return The hocon {@link ConfigurationParser}
   */
  @Nonnull
  static ConfigurationParser hocon() {
    return HoconConfigurationParser.getInstance();
  }

  /**
   * Returns a {@link ConfigurationParser} that parses {@link Configuration configs} to yaml
   * format.
   *
   * @return The yaml {@link ConfigurationParser}
   */
  @Nonnull
  static ConfigurationParser yaml() {
    return YamlConfigurationParser.getInstance();
  }

  /**
   * Writes the config map into writer in a specific form, but don't close or flush the stream.
   *
   * @param config The config as a {@link Map}
   * @param writer The writer the method should write in
   */
  void serialize(@Nonnull Map<String, Object> config, @Nonnull Writer writer) throws IOException;
}
