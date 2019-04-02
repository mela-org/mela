package com.github.stupremee.mela.store.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import org.jetbrains.annotations.Nullable;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
public class JacksonSerializer implements RedisSerializer<Object> {

  private final ObjectMapper mapper;

  private JacksonSerializer() {
    this.mapper = new ObjectMapper();
  }

  @Nullable
  @Override
  public byte[] serialize(@Nullable Object o) throws SerializationException {
    if (o == null) {
      return new byte[0];
    }

    return Try.of(() -> mapper.writeValueAsBytes(o))
        .getOrElseThrow(
            t -> new SerializationException("Failed to write Json: " + t.getMessage(), t));
  }

  @Nullable
  @Override
  public Object deserialize(@Nullable byte[] bytes) throws SerializationException {
    if (bytes == null || bytes.length == 0) {
      return null;
    }

    return Try.of(() -> mapper.readValue(bytes, Object.class))
        .getOrElseThrow(
            t -> new SerializationException("Failed to write Json: " + t.getMessage(), t));
  }

  public static RedisSerializer<Object> create() {
    return new JacksonSerializer();
  }
}
