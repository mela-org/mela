package com.github.stupremee.mela.config.jackson;

import static com.google.common.base.Preconditions.checkNotNull;

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

  /**
   * Creates a new {@link JacksonConfigAssembler} that can be used as a {@link Provider Guice
   * Provider} to bind a {@link Config}.
   */
  public static Provider<Config> create(ObjectMapper mapper, InputStream input) {
    checkNotNull(mapper, "mapper can't be null.");
    checkNotNull(input, "input can't be null.");
    return new JacksonConfigAssembler(mapper, input);
  }
}