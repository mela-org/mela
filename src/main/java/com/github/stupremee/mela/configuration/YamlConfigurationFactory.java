package com.github.stupremee.mela.configuration;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Preconditions;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.annotation.Nonnull;
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

  @NotNull
  @Override
  @Nonnull
  public ConfigurationFactory addDefault(@NotNull String path, @NotNull Object value) {
    Preconditions.checkNotNull(path, "path can't be null.");
    defaults.set(path, value);
    return this;
  }

  @NotNull
  @Override
  @Nonnull
  public ConfigurationFactory setDefaults(@NotNull Configuration defaults) {
    Preconditions.checkNotNull(defaults, "defaults can't be null.");
    this.defaults = Objects.requireNonNull(defaults);
    return this;
  }

  @NotNull
  @Override
  public Configuration load(@NotNull File file) throws IOException {
    Preconditions.checkNotNull(file, "file can't be null.");
    try (var reader = new FileReader(file, StandardCharsets.UTF_8)) {
      return load(reader);
    }
  }

  @NotNull
  @Override
  public Configuration load(@NotNull Reader reader) throws IOException {
    Preconditions.checkNotNull(reader, "reader can't be null.");
    return this.parseAndMap(reader);
  }
}
