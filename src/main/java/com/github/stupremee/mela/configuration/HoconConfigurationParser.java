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
 * @since 04.04.2019
 */
public class HoconConfigurationParser implements ConfigurationParser {

  private static class Lazy {

    private static HoconConfigurationParser INSTANCE = new HoconConfigurationParser();
  }

  private final ObjectMapper mapper;

  private HoconConfigurationParser() {
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
