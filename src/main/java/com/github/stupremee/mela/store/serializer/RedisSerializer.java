package com.github.stupremee.mela.store.serializer;

import org.jetbrains.annotations.Nullable;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
public interface RedisSerializer<T> {
  /**
   * Serialize the given object to binary data.
   *
   * @param t object to serialize. Can be {@code null}.
   * @return the equivalent binary data. Can be {@code null}.
   */
  @Nullable
  byte[] serialize(@Nullable T t) throws SerializationException;

  /**
   * Deserialize an object from the given binary data.
   *
   * @param bytes object binary representation. Can be {@code null}.
   * @return the equivalent object instance. Can be {@code null}.
   */
  @Nullable
  T deserialize(@Nullable byte[] bytes) throws SerializationException;
}
