package com.github.stupremee.mela.configuration;

import com.google.common.base.Preconditions;
import com.jasonclawson.jackson.dataformat.hocon.HoconFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 04.04.2019
 */
public class HoconConfigurationFactory extends AbstractJacksonConfigurationFactory {

  HoconConfigurationFactory() {
    super(new HoconFactory());
  }

  @Nonnull
  @Override
  public ConfigurationFactory addDefault(@Nonnull String path, @Nonnull Object value) {
    Preconditions.checkNotNull(path, "path can't be null.");
    Preconditions.checkNotNull(value, "value can't be null.");
    defaults.set(path, value);
    return this;
  }

  @Nonnull
  @Override
  public ConfigurationFactory setDefaults(@Nonnull Configuration defaults) {
    Preconditions.checkNotNull(defaults, "defaults can't be null.");
    this.defaults = defaults;
    return this;
  }

  @Nonnull
  @Override
  public Configuration load(@Nonnull File file) throws IOException {
    Preconditions.checkNotNull(file, "file can't be null.");
    try (var reader = new FileReader(file, StandardCharsets.UTF_8)) {
      return load(reader);
    }
  }

  @Nonnull
  @Override
  public Configuration load(@Nonnull Reader reader) throws IOException {
    Preconditions.checkNotNull(reader, "reader can't be null.");
    return this.parseAndMap(reader);
  }
}
