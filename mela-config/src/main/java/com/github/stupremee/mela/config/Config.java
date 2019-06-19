package com.github.stupremee.mela.config;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 18.06.19
 */
public interface Config {

  /**
   * Tries to get the {@link Object} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link Long}
   */
  Optional<Long> getLong(String path);

  /**
   * Tries to get the {@link Object} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link Short}
   */
  Optional<Short> getShort(String path);

  /**
   * Tries to get the {@link Object} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link Object}
   */
  Optional<Object> getObject(String path);

  /**
   * Tries to get the {@link Object} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link String}
   */
  Optional<String> getString(String path);

  /**
   * Tries to get the {@link Object} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link Integer}
   */
  Optional<Integer> getInteger(String path);

  /**
   * Tries to get the {@link Object} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link Number}
   */
  Optional<Number> getNumber(String path);

  /**
   * Tries to get the {@link Object} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link OffsetDateTime}
   */
  Optional<OffsetDateTime> getOffsetDateTime(String path);

  /**
   * Tries to get the {@link Object} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link Double}
   */
  Optional<Double> getDouble(String path);

  /**
   * Tries to get the {@link Object} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link Float}
   */
  Optional<Float> getFloat(String path);
}