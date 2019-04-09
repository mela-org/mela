package com.github.stupremee.mela.configuration;

import io.vavr.control.Option;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
  @Nonnull
  static Configuration empty() {
    return EmptyConfiguration.instance();
  }

  /**
   * Creates a new {@link Configuration} from all the key/value pairs that can be found in the
   * dotted path.
   *
   * @param path The dotted path as a getString
   * @return The getSection of the path as a {@link Configuration}
   */
  @Nonnull
  Configuration getSection(@Nonnull String path);

  /**
   * Sets the value at the dotted path.
   *
   * @param path The dotted path
   * @param value The value
   */
  void set(@Nonnull String path, @Nonnull Object value);

  /**
   * Returns the value at the dotted path as {@link T}.
   *
   * @param path The dotted path to the value
   * @param <T> The type of the value that at the dotted path
   * @return The value as {@link T}
   */
  @Nonnull
  default <T> Option<T> get(@Nonnull String path) {
    return get(path, this.<T>getDefaultValue(path).getOrNull());
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
  @Nonnull
  <T> Option<T> get(@Nonnull String path, @Nullable T def);

  /**
   * Returns a getList as {@link Flux} of unknown objects at the dotted path.
   *
   * @param path The dotted path to the value
   * @return The list of objects as a {@link Collection} or an empty list if the key does not exists
   */
  @Nonnull
  Collection<?> getList(@Nonnull String path);

  /**
   * Checks if a key-value pair exists.
   *
   * @param path The dotted path to the key-value pair
   * @return <code>true</code> if the key-value pair exists and <code>false</code> if the key-value
   *     pair dont exist
   */
  boolean exists(@Nonnull String path);

  /**
   * Tries to cast the value at the dotted path to object and returns it.
   *
   * @see #getObject(String, Object)
   */
  @Nonnull
  default Option<Object> getObject(@Nonnull String path) {
    return getObject(path, this.getDefaultValue(path).getOrNull());
  }

  /**
   * Tries to cast the value at the dotted path to object and returns it.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default value that will be returned if the key-value pair doesn't exist
   * @return The object as {@link Option} or an empty {@link Option} if the key-value pair doesn't
   *     exist.
   */
  @Nonnull
  Option<Object> getObject(@Nonnull String path, @Nullable Object def);

  /**
   * Returns a list of objects as a {@link Flux}.
   *
   * @param path The dotted path
   * @return The getList as a {@link Flux}
   */
  @Nonnull
  Collection<Object> getObjects(@Nonnull String path);

  /**
   * Returns {@link #getString(String, String)} with an empty string as the default parameter.
   *
   * @see #getString(String, String)
   */
  @Nonnull
  default Option<String> getString(@Nonnull String path) {
    return getString(path, this.<String>getDefaultValue(path).getOrNull());
  }

  /**
   * Tries to cast the value at the dotted path to {@link String} and returns it.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default value that will be returned if the key-value pair doesn't exist
   * @return The string as {@link Option} or an empty {@link Option} if the key-value pair doesn't
   *     exist or the value isn't a getString.
   */
  @Nonnull
  Option<String> getString(@Nonnull String path, @Nullable String def);

  /**
   * Returns a list of strings as a {@link Flux}.
   *
   * @param path The dotted path
   * @return The getList as a {@link Flux}
   */
  @Nonnull
  Collection<String> getStrings(@Nonnull String path);

  /**
   * Returns {@link #getNumber(String, Number)} with {@code null} as the default parameter.
   *
   * @see #getNumber(String, Number)
   */
  @Nonnull
  default Option<Number> getNumber(@Nonnull String path) {
    return getNumber(path, this.<Number>getDefaultValue(path).getOrNull());
  }

  /**
   * Tries to cast the value at the dotted path to {@link Number} and returns it.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default value that will be returned if the key-value pair doesn't exist
   * @return The number as {@link Option} or an empty {@link Option} if the key-value pair doesn't
   *     exist or the value isn't a getNumber.
   */
  @Nonnull
  Option<Number> getNumber(@Nonnull String path, @Nullable Number def);

  /**
   * Returns a list of numbers as a {@link Flux}.
   *
   * @param path The dotted path
   * @return The getList as a {@link Flux}
   */
  @Nonnull
  Collection<Number> getNumbers(@Nonnull String path);

  /**
   * Returns {@link #getBool(String, Boolean)} with <code>false</code> as the default parameter.
   *
   * @see #getBool(String, Boolean)
   */
  @Nonnull
  default Option<Boolean> getBool(@Nonnull String path) {
    return getBool(path, this.<Boolean>getDefaultValue(path).getOrNull());
  }

  /**
   * Tries to cast the value at the dotted path to {@link Boolean} and returns it.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default value that will be returned if the key-value pair doesn't exist
   * @return The boolean as {@link Option} or an empty {@link Option} if the key-value pair doesn't
   *     exist or the value isn't a boolean.
   */
  @Nonnull
  Option<Boolean> getBool(@Nonnull String path, @Nullable Boolean def);

  /**
   * Returns a list of booleans as a {@link Flux}.
   *
   * @param path The dotted path
   * @return The getList as a {@link Flux}
   */
  @Nonnull
  Collection<Boolean> getBools(@Nonnull String path);

  /**
   * Returns all keys in the current configuration.
   *
   * @return The getList of getKeys
   */
  @Nonnull
  Collection<String> getKeys();

  /**
   * Deletes the key-value pair at the dotted path.
   *
   * @param path The dotted path to the pair
   */
  void remove(@Nonnull String path);

  /**
   * Returns the value at the dotted path that comes from the setDefaults.
   *
   * @param path The dotted path
   * @return The getObject taken from the setDefaults
   */
  <T> Option<T> getDefaultValue(@Nonnull String path);

  /**
   * Returns the parsed config as getString by using a {@link java.io.StringWriter} and the given
   * {@link ConfigurationParser}.
   *
   * @param parser The {@link ConfigurationParser} that will be used to serialize the {@link
   *     Configuration}
   * @return The configuration parsed to a getString
   */
  @Nonnull
  String writeToString(@Nonnull ConfigurationParser parser) throws IOException;

  /**
   * Writes the configuration into the writer using the given {@link ConfigurationParser} and will
   * close the writer after writing to it.
   *
   * @param writer The {@link Writer} the config should be written in
   * @param parser The {@link ConfigurationParser} that will be used to serialize the
   *     configuration
   */
  void write(@Nonnull Writer writer, @Nonnull ConfigurationParser parser) throws IOException;
}
