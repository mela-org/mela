package com.github.stupremee.mela.config.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stupremee.mela.config.Config;
import com.google.inject.Provider;
import com.google.inject.ProvisionException;
import java.io.IOException;
import java.io.InputStream;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 21.06.19
 */
final class JsonConfigProvider implements Provider<Config> {

  private final ObjectMapper mapper;
  private final InputStream input;

  JsonConfigProvider(ObjectMapper mapper, InputStream input) {
    this.mapper = mapper;
    this.input = input;
  }

  @Override
  public Config get() {
    try {
      JsonNode node = mapper.readTree(input);
      return new JsonConfig(node);
    } catch (IOException cause) {
      throw new ProvisionException("Failed to get Config.", cause);
    }
  }
}