package com.github.stupremee.mela.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
public class YamlConfigurationParser implements ConfigurationParser {

  private static class Lazy {

    private static YamlConfigurationParser INSTANCE = new YamlConfigurationParser();
  }

  private final ObjectMapper mapper;

  private YamlConfigurationParser() {
    this.mapper = new ObjectMapper(new YAMLFactory());
  }

  @Override
  public void serialize(@Nonnull Map<String, Object> config, @Nonnull Writer writer)
      throws IOException {
    Preconditions.checkNotNull(config, "config can't be null.");
    Preconditions.checkNotNull(writer, "writer can't be null.");
    mapper.writeValue(writer, config);
  }

  static ConfigurationParser getInstance() {
    return Lazy.INSTANCE;
  }
}
