package com.github.stupremee.mela.serialization.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stupremee.mela.beans.Bean;
import com.github.stupremee.mela.util.Loggers;
import com.google.common.base.Preconditions;
import io.vavr.control.Try;
import java.io.Reader;
import java.io.StringReader;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
public class JsonBeanDeserializer implements BeanDeserializer {

  private static class Lazy {

    private static JsonBeanDeserializer INSTANCE = new JsonBeanDeserializer();
  }

  private static final Logger LOG = Loggers.getLogger("BeanDeserializer");
  private final ObjectMapper mapper;

  private JsonBeanDeserializer() {
    this.mapper = new ObjectMapper();
  }

  @NotNull
  @Override
  public <T extends Bean> Optional<T> deserialize(@NotNull Class<T> beanClass,
      @NotNull Reader reader) {
    Preconditions.checkNotNull(beanClass, "beanClass can't be null.");
    Preconditions.checkNotNull(reader, "reader can't be null.");

    return Try.of(() -> mapper.readValue(reader, beanClass))
        .andFinallyTry(reader::close)
        .onFailure(t -> LOG.error("Error while deserializing bean"))
        .toJavaOptional();
  }

  @NotNull
  @Override
  public <T extends Bean> Optional<T> deserialize(@NotNull Class<T> beanClass,
      @NotNull String value) {
    return deserialize(beanClass, new StringReader(value));
  }

  static BeanDeserializer getInstance() {
    return Lazy.INSTANCE;
  }
}
