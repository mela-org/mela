package com.github.stupremee.mela.configuration;

import com.github.stupremee.mela.util.Loggers;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import me.grison.jtoml.impl.Toml;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 23.03.2019
 */
public class TomlConfigurationFactory implements ConfigurationFactory {

  private final Logger log = Loggers.logger();
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
    return load(new FileReader(file));
  }

  @Override
  @SuppressWarnings("unchecked")
  public Configuration load(Reader reader) throws IOException {
    int c;
    StringBuilder string = new StringBuilder();
    while ((c = reader.read()) != -1) {
      string.append((char) c);
    }

    Toml toml = Toml.parse(string.toString());

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
