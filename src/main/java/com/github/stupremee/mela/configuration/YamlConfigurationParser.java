package com.github.stupremee.mela.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

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
    SimpleModule module = new SimpleModule();
    module.addSerializer(MemoryConfiguration.class, MemoryConfiguration.getSerializer());
    mapper.registerModule(module);
  }

  @Override
  public void serialize(@NotNull Map<String, Object> config, @NotNull Writer writer)
      throws IOException {
    mapper.writeValue(writer, config);
  }

  public static ConfigurationParser getInstance() {
    return Lazy.INSTANCE;
  }
}
