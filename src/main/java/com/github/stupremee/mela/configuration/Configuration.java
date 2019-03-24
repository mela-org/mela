package com.github.stupremee.mela.configuration;

import io.vavr.control.Option;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 23.03.2019
 */
@SuppressWarnings("unused")
public interface Configuration {

  char SEPARATOR = '.';

  /**
   * Creates an empty configuration.
   *
   * @return The empty configuration
   */
  static Configuration empty() {
    return EmptyConfiguration.instance();
  }

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
   * Sets the value at the dotted path.
   *
   * @param path The dotted path
   * @param value The value
   */
  void set(String path, Object value);

  /**
   * Returns the value at the dotted path as {@link T}.
   *
   * @param path The dotted path to the value
   * @param <T> The type of the value that at the dotted path
   * @return The value as {@link T}
   */
  @NotNull
  default <T> Option<T> get(String path) {
    return get(path, this.<T>defaultValue(path).getOrNull());
  }

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
   * Returns a list as {@link Flux} of unknown objects at the dotted path.
   *
   * @param path The dotted path to the value
   * @return The list of objects as {@link Flux} or an empty list if the key does not exists
   */
  @NotNull
  Collection<?> list(String path);

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
    return object(path, this.defaultValue(path).getOrNull());
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
   * Returns a list of objects as a {@link Flux}.
   *
   * @param path The dotted path
   * @return The list as a {@link Flux}
   */
  @NotNull
  Collection<Object> objects(String path);

  /**
   * Returns {@link #string(String, String)} with an empty string as the default parameter.
   *
   * @see #string(String, String)
   */
  @NotNull
  default Option<String> string(String path) {
    return string(path, this.<String>defaultValue(path).getOrNull());
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
   * Returns a list of strings as a {@link Flux}.
   *
   * @param path The dotted path
   * @return The list as a {@link Flux}
   */
  @NotNull
  Collection<String> strings(String path);

  /**
   * Returns {@link #number(String, Number)} with 0 as the default parameter.
   *
   * @see #number(String, Number)
   */
  @NotNull
  default Option<Number> number(String path) {
    return number(path, this.<Number>defaultValue(path).getOrNull());
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
   * Returns a list of numbers as a {@link Flux}.
   *
   * @param path The dotted path
   * @return The list as a {@link Flux}
   */
  @NotNull
  Collection<Number> numbers(String path);

  /**
   * Returns {@link #bool(String, Boolean)} with <code>false</code> as the default parameter.
   *
   * @see #bool(String, Boolean)
   */
  @NotNull
  default Option<Boolean> bool(String path) {
    return bool(path, this.<Boolean>defaultValue(path).getOrNull());
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
   * Returns a list of booleans as a {@link Flux}.
   *
   * @param path The dotted path
   * @return The list as a {@link Flux}
   */
  @NotNull
  Collection<Boolean> bools(String path);

  /**
   * Returns all keys in the current configuration.
   *
   * @return The list of keys
   */
  @NotNull
  Collection<String> keys();

  /**
   * Deletes the key-value pair at the dotted path.
   *
   * @param path The dotted path to the pair
   */
  void remove(String path);

  /**
   * Returns the value at the dotted path that comes from the defaults.
   *
   * @param path The dotted path
   * @return The object taken from the defaults
   */
  <T> Option<T> defaultValue(String path);
}
