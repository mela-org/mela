package com.github.stupremee.mela.serialization.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stupremee.mela.beans.SnowflakeBean;
import com.github.stupremee.mela.util.Loggers;
import com.google.common.base.Preconditions;
import io.vavr.control.Try;
import java.io.Reader;
import java.io.StringReader;
import java.util.Optional;
import javax.annotation.Nonnull;
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

  @Nonnull
  @Override
  public <T extends SnowflakeBean> Optional<T> deserialize(@Nonnull Class<T> beanClass,
      @Nonnull Reader reader) {
    Preconditions.checkNotNull(beanClass, "beanClass can't be null.");
    Preconditions.checkNotNull(reader, "reader can't be null.");

    return Try.of(() -> mapper.readValue(reader, beanClass))
        .andFinallyTry(reader::close)
        .onFailure(t -> LOG.error("Error while deserializing bean"))
        .toJavaOptional();
  }

  @Nonnull
  @Override
  public <T extends SnowflakeBean> Optional<T> deserialize(@Nonnull Class<T> beanClass,
      @Nonnull String value) {
    return deserialize(beanClass, new StringReader(value));
  }

  @Nonnull
  static BeanDeserializer getInstance() {
    return Lazy.INSTANCE;
  }
}
