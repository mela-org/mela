package com.github.stupremee.mela.configuration;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
public class YamlConfigurationFactory extends AbstractJacksonConfigurationFactory {

  private YamlConfigurationFactory() {
    super(new YAMLFactory());
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
  public Configuration load(Reader reader) throws IOException {
    return this.parseAndMap(reader);
  }

  public static ConfigurationFactory create() {
    return new YamlConfigurationFactory();
  }
}
