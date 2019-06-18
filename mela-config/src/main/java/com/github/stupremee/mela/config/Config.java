package com.github.stupremee.mela.config;

import com.github.stupremee.mela.config.exceptions.InvalidTypeException;
import java.time.Duration;
import java.time.Period;
import java.util.Optional;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 18.06.19
 */
public interface Config {

  /**
   * Tries to get the {@link Long} at the dotted path.
   *
   * @throws InvalidTypeException if the type is not a {@link Long}
   */
  Optional<Long> getLong(String path);
  /**
   * Tries to get the {@link Short} at the dotted path.
   *
   * @throws InvalidTypeException if the type is not a {@link Short}
   */
  Optional<Short> getShort(String path);
  /**
   * Tries to get the {@link Object} at the dotted path.
   *
   * @throws InvalidTypeException if the type is not a {@link Object}
   */
  Optional<Object> getObject(String path);
  /**
   * Tries to get the {@link String} at the dotted path.
   *
   * @throws InvalidTypeException if the type is not a {@link String}
   */
  Optional<String> getString(String path);
  /**
   * Tries to get the {@link Integer} at the dotted path.
   *
   * @throws InvalidTypeException if the type is not a {@link Integer}
   */
  Optional<Integer> getInteger(String path);
  /**
   * Tries to get the {@link Number} at the dotted path.
   *
   * @throws InvalidTypeException if the type is not a {@link Number}
   */
  Optional<Number> getNumber(String path);
  /**
   * Tries to get the {@link Duration} at the dotted path.
   *
   * @throws InvalidTypeException if the type is not a {@link Duration}
   */
  Optional<Duration> getDuration(String path);
  /**
   * Tries to get the {@link Period} at the dotted path.
   *
   * @throws InvalidTypeException if the type is not a {@link Period}
   */
  Optional<Period> getPeriod(String path);


}