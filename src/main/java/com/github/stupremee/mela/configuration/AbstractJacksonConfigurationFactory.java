package com.github.stupremee.mela.configuration;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 04.04.2019
 */
public abstract class AbstractJacksonConfigurationFactory implements ConfigurationFactory {

  protected final ObjectMapper mapper;
  protected Configuration defaults;

  protected AbstractJacksonConfigurationFactory(JsonFactory factory) {
    this.mapper = new ObjectMapper(factory);
    this.defaults = new MemoryConfiguration(Configuration.empty());
  }

  @SuppressWarnings("unchecked")
  protected MemoryConfiguration parseAndMap(Reader reader) throws IOException {
    Map<String, Object> map = mapper.readValue(reader, java.util.Map.class);
    map.forEach((key, value) -> {
      if (value instanceof Map<?, ?>) {
        map.put(key, new MemoryConfiguration((Map<String, Object>) value, defaults));
      }
    });

    reader.close();
    return new MemoryConfiguration(map, defaults);
  }
}
