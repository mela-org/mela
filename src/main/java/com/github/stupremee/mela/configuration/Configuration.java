package com.github.stupremee.mela.configuration;

import io.vavr.control.Option;
import java.util.Collection;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 23.03.2019
 */
public interface Configuration {

  /**
   * Creates a new {@link Configuration} from all the key/value pairs that can be found in the
   * dotted path.
   *
   * @param path The dotted path as a string
   * @return The section of the path as a {@link Configuration}
   */
  @NotNull
  Configuration section(String path);

  /**
   * Returns the value at the dotted path as {@link T}.
   *
   * @param path The dotted path to the value
   * @param def The default will be returned if the key does not exists or the value of the path
   *     is not type of {@link T}
   * @param <T> The type of the value that at the dotted path
   * @return The value as {@link T}
   */
  @NotNull
  <T> Option<T> get(String path, T def);

  /**
   * Returns a list of objects at the dotted path.
   *
   * @param path The dotted path to the value
   * @return The list of objects or an empty list if the key does not exists
   * @see #list(String, Collection)
   */
  @NotNull
  default Collection<Object> list(String path) {
    return list(path, Collections.emptyList());
  }

  /**
   * Returns a list of objects that can be found at the dotted path.
   *
   * @param path The dotted path to the value
   * @param def The default will be returned if the key does not exist
   * @return The list of objects or an empty list if the key does not exists
   */
  @NotNull
  Collection<Object> list(String path, Collection<?> def);

  /**
   * Checks if a key-value pair exists.
   *
   * @param path The dotted path to the key-value pair
   * @return <code>true</code> if the key-value pair exists and <code>false</code> if the key-value
   *     pair dont exist
   */
  boolean exists(String path);

  /**
   * Tries to cast the value at the dotted path to object and returns it.
   *
   * @see #object(String, Object)
   */
  @NotNull
  default Option<Object> object(String path) {
    return object(path, null);
  }

  /**
   * Tries to cast the value at the dotted path to object and returns it.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default value that will be returned if the key-value pair doesn't exist
   * @return The object as {@link Option} or an empty {@link Option} if the key-value pair doesn't
   *     exist.
   */
  @NotNull
  Option<Object> object(String path, Object def);

  /**
   * Returns {@link #object(String, Object)} with an empty and immutable list as default.
   *
   * @see #objects(String, Collection)
   */
  @NotNull
  default Collection<Object> objects(String path) {
    return objects(path, Collections.emptyList());
  }

  /**
   * Returns a list of object.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default list that will be returned
   * @return The list of objects or an empty list if the key-value pair doesn't exist.
   */
  @NotNull
  Collection<Object> objects(String path, Collection<Object> def);

  /**
   * Returns {@link #string(String, String)} with an empty string as the default parameter.
   *
   * @see #string(String, String)
   */
  @NotNull
  default Option<String> string(String path) {
    return string(path, "");
  }

  /**
   * Tries to cast the value at the dotted path to {@link String} and returns it.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default value that will be returned if the key-value pair doesn't exist
   * @return The string as {@link Option} or an empty {@link Option} if the key-value pair doesn't
   *     exist or the value isn't a string.
   */
  @NotNull
  Option<String> string(String path, String def);

  /**
   * Returns {@link #strings(String, Collection)} with an empty and immutable list as the default
   * parameter.
   *
   * @see #strings(String, Collection)
   */
  @NotNull
  default Collection<String> strings(String path) {
    return strings(path, Collections.emptyList());
  }

  /**
   * Returns a list of strings. Values that aren't a string will be ignored.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default list that will be returned
   * @return The list of strings or an empty list if the key-value pair doesn't exist.
   */
  @NotNull
  Collection<String> strings(String path, Collection<String> def);

  /**
   * Returns {@link #number(String, Number)} with 0 as the default parameter.
   *
   * @see #number(String, Number)
   */
  @NotNull
  default Option<Number> number(String path) {
    return number(path, 0);
  }

  /**
   * Tries to cast the value at the dotted path to {@link Number} and returns it.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default value that will be returned if the key-value pair doesn't exist
   * @return The number as {@link Option} or an empty {@link Option} if the key-value pair doesn't
   *     exist or the value isn't a number.
   */
  @NotNull
  Option<Number> number(String path, Number def);

  /**
   * Returns {@link #numbers(String, Collection)} with an empty and immutable list as the default
   * parameter.
   *
   * @see #numbers(String, Collection)
   */
  @NotNull
  default Collection<Number> numbers(String path) {
    return numbers(path, Collections.emptyList());
  }

  /**
   * Returns a list of numbers. Values that aren't a number will be ignored.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default list that will be returned
   * @return The list of numbers or an empty list if the key-value pair doesn't exist.
   */
  @NotNull
  Collection<Number> numbers(String path, Collection<Number> def);

  /**
   * Returns {@link #bool(String, Boolean)} with <code>false</code> as the default parameter.
   *
   * @see #bool(String, Boolean)
   */
  @NotNull
  default Option<Boolean> bool(String path) {
    return bool(path, false);
  }

  /**
   * Tries to cast the value at the dotted path to {@link Boolean} and returns it.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default value that will be returned if the key-value pair doesn't exist
   * @return The boolean as {@link Option} or an empty {@link Option} if the key-value pair doesn't
   *     exist or the value isn't a boolean.
   */
  @NotNull
  Option<Boolean> bool(String path, Boolean def);

  /**
   * Returns {@link #bools(String, Collection)} with an empty and immutable list as the default
   * parameter.
   *
   * @see #bools(String, Collection)
   */
  @NotNull
  default Collection<Boolean> bools(String path) {
    return bools(path, Collections.emptyList());
  }

  /**
   * Returns a list of booleans. Values that aren't a boolean will be ignored.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default list that will be returned
   * @return The list of booleans or an empty list if the key-value pair doesn't exist.
   */
  @NotNull
  Collection<Boolean> bools(String path, Collection<Boolean> def);
}
