package com.github.stupremee.mela.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import me.grison.jtoml.impl.Toml;
import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 23.03.2019
 */
public final class TomlConfigurationFactory implements ConfigurationFactory {

  private Configuration defaults;

  private TomlConfigurationFactory() {
    this.defaults = new MemoryConfiguration(Configuration.empty());
  }

  @NotNull
  @Override
  public ConfigurationFactory addDefault(String path, Object value) {
    defaults.set(path, value);
    return this;
  }

  @NotNull
  @Override
  public ConfigurationFactory defaults(Configuration defaults) {
    this.defaults = defaults;
    return this;
  }

  @Override
  public Configuration load(File file) throws IOException {
    try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
      return load(reader);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Configuration load(Reader reader) throws IOException {
    StringBuilder buffer = new StringBuilder();
    for (int i = 0; i != -1; i = reader.read()) {
      buffer.append((char) i);
    }
    reader.close();

    Toml toml = Toml.parse(buffer.toString());

    Map<String, Object> map = toml.getMap("");
    map.forEach((key, value) -> {
      if (value instanceof Map<?, ?>) {
        map.put(key, new MemoryConfiguration((Map<String, Object>) value, defaults));
      }
    });
    return new MemoryConfiguration(map, defaults);
  }

  public static ConfigurationFactory create() {
    return new TomlConfigurationFactory();
  }
}
