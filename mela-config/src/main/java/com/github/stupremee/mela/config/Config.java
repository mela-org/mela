package com.github.stupremee.mela.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 18.06.19
 */
public interface Config {

  /**
   * Tries to get the {@link String} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link String}
   */
  Optional<String> getString(String path);

  /**
   * Tries to get a {@link Collection String list} at the dotted path.
   *
   * @return A {@link Collection} containing all elements or none if the value was invalid or absent
   */
  Collection<String> getStringList(String path);

  /**
   * Tries to get the {@link Number} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link Number}
   */
  Optional<Number> getNumber(String path);

  /**
   * Tries to get a {@link Collection Number list} at the dotted path.
   *
   * @return A {@link Collection} containing all elements or none if the value was invalid or absent
   */
  Collection<Number> getNumberList(String path);

  /**
   * Tries to get the {@link Integer} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link Integer}
   */
  OptionalInt getInteger(String path);

  /**
   * Tries to get a {@link Collection Integer list} at the dotted path.
   *
   * @return A {@link Collection} containing all elements or none if the value was invalid or absent
   */
  Collection<Integer> getIntegerList(String path);

  /**
   * Tries to get the {@link Double} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link Double}
   */
  OptionalDouble getDouble(String path);

  /**
   * Tries to get a {@link Collection Double list} at the dotted path.
   *
   * @return A {@link Collection} containing all elements or none if the value was invalid or absent
   */
  Collection<Double> getDoubleList(String path);

  /**
   * Tries to get the {@link Long} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link Long}
   */
  OptionalLong getLong(String path);

  /**
   * Tries to get a {@link Collection Long list} at the dotted path.
   *
   * @return A {@link Collection} containing all elements or none if the value was invalid or absent
   */
  Collection<Long> getLongList(String path);

  /**
   * Tries to get the {@link BigInteger} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link BigInteger}
   */
  Optional<BigInteger> getBigInteger(String path);

  /**
   * Tries to get a {@link Collection BigInteger list} at the dotted path.
   *
   * @return A {@link Collection} containing all elements or none if the value was invalid or absent
   */
  Collection<BigInteger> getBigIntegerList(String path);

  /**
   * Tries to get the {@link BigDecimal} at the dotted path.
   *
   * @return An {@link Optional} that is empty if the value is absent or the value can't be parsed
   *     to a {@link BigDecimal}
   */
  Optional<BigDecimal> getBigDecimal(String path);

  /**
   * Tries to get a {@link Collection BigInteger list} at the dotted path.
   *
   * @return A {@link Collection} containing all elements or none if the value was invalid or absent
   */
  Collection<BigDecimal> getBigDecimalList(String path);

}