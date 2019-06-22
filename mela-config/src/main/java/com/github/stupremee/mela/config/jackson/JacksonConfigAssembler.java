package com.github.stupremee.mela.config.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stupremee.mela.config.Config;
import com.github.stupremee.mela.config.annotations.ConfigMapper;
import com.github.stupremee.mela.config.annotations.ConfigSource;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.ProvisionException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 21.06.19
 */
public final class JacksonConfigAssembler implements Provider<Config> {

  private final ObjectMapper mapper;
  private final InputStream input;

  @Inject
  JacksonConfigAssembler(@ConfigMapper ObjectMapper mapper, @ConfigSource InputStream input) {
    this.mapper = mapper;
    this.input = input;
  }

  @Override
  public Config get() {
    try {
      JsonNode node = mapper.readTree(input);
      return new JacksonConfig(node, mapper);
    } catch (IOException cause) {
      throw new ProvisionException("Failed to get Config.", cause);
    }
  }
}