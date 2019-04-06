package com.github.stupremee.mela.configuration;

import com.jasonclawson.jackson.dataformat.hocon.HoconFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.jetbrains.annotations.NotNull;

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

  @Override
  public @NotNull ConfigurationFactory addDefault(String path, Object value) {
    defaults.set(path, value);
    return this;
  }

  @Override
  public @NotNull ConfigurationFactory setDefaults(Configuration defaults) {
    this.defaults = defaults;
    return this;
  }

  @Override
  public Configuration load(File file) throws IOException {
    return load(new FileReader(file));
  }

  @Override
  public Configuration load(Reader reader) throws IOException {
    return this.parseAndMap(reader);
  }
}
