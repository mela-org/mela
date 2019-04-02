package com.github.stupremee.mela.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
public class ConfigurationSerializer extends StdSerializer<Configuration> {

  private ConfigurationSerializer() {
    super(Configuration.class);
  }

  @Override
  public void serialize(Configuration value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    
  }
}
