package com.github.stupremee.mela.config.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stupremee.mela.config.Config;
import com.github.stupremee.mela.config.ConfigProvider;
import com.google.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 19.06.19
 */
final class JsonConfigProvider implements ConfigProvider {

  private final ObjectMapper mapper;

  @Inject
  JsonConfigProvider(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public <T> T parse(InputStream input, Class<T> configType) {
    try {
      return mapper.readValue(input, configType);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Config load(InputStream input) {
    try {
      return new JsonConfig(mapper.readTree(input));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
