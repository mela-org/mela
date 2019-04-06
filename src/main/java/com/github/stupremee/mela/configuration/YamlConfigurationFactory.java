package com.github.stupremee.mela.configuration;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Preconditions;
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

  YamlConfigurationFactory() {
    super(new YAMLFactory());
  }

  @Override
  @NotNull
  public ConfigurationFactory addDefault(String path, Object value) {
    Preconditions.checkNotNull(path, "path can't be null.");
    defaults.set(path, value);
    return this;
  }

  @Override
  @NotNull
  public ConfigurationFactory setDefaults(Configuration defaults) {
    Preconditions.checkNotNull(defaults, "defaults can't be null.");
    this.defaults = Objects.requireNonNull(defaults);
    return this;
  }

  @Override
  public Configuration load(File file) throws IOException {
    Preconditions.checkNotNull(file, "file can't be null.");
    try (var reader = new FileReader(file, StandardCharsets.UTF_8)) {
      return load(reader);
    }
  }

  @Override
  public Configuration load(Reader reader) throws IOException {
    Preconditions.checkNotNull(reader, "reader can't be null.");
    return this.parseAndMap(reader);
  }
}
