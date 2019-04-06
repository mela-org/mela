package com.github.stupremee.mela.serialization.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stupremee.mela.beans.Bean;
import com.github.stupremee.mela.util.Loggers;
import com.google.common.base.Preconditions;
import io.vavr.control.Try;
import java.io.Writer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
public class JsonBeanSerializer implements BeanSerializer {

  private static class Lazy {

    private static JsonBeanSerializer INSTANCE = new JsonBeanSerializer();
  }

  private static final Logger LOG = Loggers.getLogger("BeanSerializer");
  private final ObjectMapper mapper;

  private JsonBeanSerializer() {
    this.mapper = new ObjectMapper();
  }

  @Override
  public void serialize(@NotNull Writer writer, @NotNull Bean bean) {
    Preconditions.checkNotNull(writer, "writer can't be null.");
    Preconditions.checkNotNull(bean, "bean can't be null.");

    Try.run(() -> writer.write(serialize(bean)))
        .onFailure(t -> LOG.error("Failed to write to writer or to serialize the bean.", t))
        .andFinallyTry(writer::flush)
        .andFinallyTry(writer::close);
  }

  @NotNull
  @Override
  public String serialize(@NotNull Bean bean) {
    Preconditions.checkNotNull(bean, "bean can't be null.");

    try {
      return mapper.writeValueAsString(bean);
    } catch (JsonProcessingException e) {
      LOG.error("Error while serializing bean to json.", e);
      return "{}";
    }
  }

  static BeanSerializer getInstance() {
    return Lazy.INSTANCE;
  }
}
