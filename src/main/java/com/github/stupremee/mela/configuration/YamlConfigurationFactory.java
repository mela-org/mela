package com.github.stupremee.mela.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
public class YamlConfigurationFactory implements ConfigurationFactory {

  private final ObjectMapper mapper;
  private Configuration defaults;

  private YamlConfigurationFactory() {
    this.defaults = new MemoryConfiguration(Configuration.empty());
    this.mapper = new ObjectMapper(new YAMLFactory());
  }

  @Override
  @NotNull
  public ConfigurationFactory addDefault(String path, Object value) {
    defaults.set(path, value);
    return this;
  }

  @Override
  @NotNull
  public ConfigurationFactory setDefaults(Configuration defaults) {
    this.defaults = Objects.requireNonNull(defaults);
    return this;
  }

  @Override
  public Configuration load(File file) throws IOException {
    return load(new FileReader(file, StandardCharsets.UTF_8));
  }

  @Override
  @SuppressWarnings("unchecked")
  public Configuration load(Reader reader) throws IOException {
    Map<String, Object> map = mapper.readValue(reader, java.util.Map.class);
    map.forEach((key, value) -> {
      if (value instanceof Map<?, ?>) {
        map.put(key, new MemoryConfiguration((Map<String, Object>) value, defaults));
      }
    });

    return new MemoryConfiguration(map, defaults);
  }

  public static ConfigurationFactory create() {
    return new YamlConfigurationFactory();
  }
}
