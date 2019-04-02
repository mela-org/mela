package com.github.stupremee.mela.configuration;

import io.vavr.control.Option;
import java.io.IOException;
import java.io.Writer;
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
   * @param path The dotted path as a getString
   * @return The getSection of the path as a {@link Configuration}
   */
  @NotNull
  Configuration getSection(String path);

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
  @NotNull <T> Option<T> get(String path, T def);

  /**
   * Returns a getList as {@link Flux} of unknown getObjects at the dotted path.
   *
   * @param path The dotted path to the value
   * @return The getList of getObjects as {@link Flux} or an empty getList if the key does not exists
   */
  @NotNull
  Collection<?> getList(String path);

  /**
   * Checks if a key-value pair exists.
   *
   * @param path The dotted path to the key-value pair
   * @return <code>true</code> if the key-value pair exists and <code>false</code> if the key-value
   *     pair dont exist
   */
  boolean exists(String path);

  /**
   * Tries to cast the value at the dotted path to getObject and returns it.
   *
   * @see #getObject(String, Object)
   */
  @NotNull
  default Option<Object> getObject(String path) {
    return getObject(path, this.getDefaultValue(path).getOrNull());
  }

  /**
   * Tries to cast the value at the dotted path to getObject and returns it.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default value that will be returned if the key-value pair doesn't exist
   * @return The getObject as {@link Option} or an empty {@link Option} if the key-value pair doesn't
   *     exist.
   */
  @NotNull
  Option<Object> getObject(String path, Object def);

  /**
   * Returns a getList of getObjects as a {@link Flux}.
   *
   * @param path The dotted path
   * @return The getList as a {@link Flux}
   */
  @NotNull
  Collection<Object> getObjects(String path);

  /**
   * Returns {@link #getString(String, String)} with an empty getString as the default parameter.
   *
   * @see #getString(String, String)
   */
  @NotNull
  default Option<String> getString(String path) {
    return getString(path, this.<String>getDefaultValue(path).getOrNull());
  }

  /**
   * Tries to cast the value at the dotted path to {@link String} and returns it.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default value that will be returned if the key-value pair doesn't exist
   * @return The getString as {@link Option} or an empty {@link Option} if the key-value pair doesn't
   *     exist or the value isn't a getString.
   */
  @NotNull
  Option<String> getString(String path, String def);

  /**
   * Returns a getList of getStrings as a {@link Flux}.
   *
   * @param path The dotted path
   * @return The getList as a {@link Flux}
   */
  @NotNull
  Collection<String> getStrings(String path);

  /**
   * Returns {@link #getNumber(String, Number)} with 0 as the default parameter.
   *
   * @see #getNumber(String, Number)
   */
  @NotNull
  default Option<Number> getNumber(String path) {
    return getNumber(path, this.<Number>getDefaultValue(path).getOrNull());
  }

  /**
   * Tries to cast the value at the dotted path to {@link Number} and returns it.
   *
   * @param path The dotted path to the key-value pair
   * @param def The default value that will be returned if the key-value pair doesn't exist
   * @return The getNumber as {@link Option} or an empty {@link Option} if the key-value pair doesn't
   *     exist or the value isn't a getNumber.
   */
  @NotNull
  Option<Number> getNumber(String path, Number def);

  /**
   * Returns a getList of getNumbers as a {@link Flux}.
   *
   * @param path The dotted path
   * @return The getList as a {@link Flux}
   */
  @NotNull
  Collection<Number> getNumbers(String path);

  /**
   * Returns {@link #getBool(String, Boolean)} with <code>false</code> as the default parameter.
   *
   * @see #getBool(String, Boolean)
   */
  @NotNull
  default Option<Boolean> getBool(String path) {
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
  @NotNull
  Option<Boolean> getBool(String path, Boolean def);

  /**
   * Returns a getList of booleans as a {@link Flux}.
   *
   * @param path The dotted path
   * @return The getList as a {@link Flux}
   */
  @NotNull
  Collection<Boolean> getBools(String path);

  /**
   * Returns all getKeys in the current configuration.
   *
   * @return The getList of getKeys
   */
  @NotNull
  Collection<String> getKeys();

  /**
   * Deletes the key-value pair at the dotted path.
   *
   * @param path The dotted path to the pair
   */
  void remove(String path);

  /**
   * Returns the value at the dotted path that comes from the setDefaults.
   *
   * @param path The dotted path
   * @return The getObject taken from the setDefaults
   */
  <T> Option<T> getDefaultValue(String path);

  /**
   * Returns the parsed config as getString by using a {@link java.io.StringWriter} and the given
   * {@link ConfigurationParser}.
   *
   * @param parser The {@link ConfigurationParser} that will be used to serialize the {@link
   *     Configuration}
   * @return The configuration parsed to a getString
   */
  @NotNull
  String writeToString(ConfigurationParser parser) throws IOException;

  /**
   * Writes the configuration into the writer using the given {@link ConfigurationParser} and will
   * close the writer after writing to it.
   *
   * @param writer The {@link Writer} the config should be written in
   * @param parser The {@link ConfigurationParser} that will be used to serialize the configuration
   */
  void write(Writer writer, ConfigurationParser parser) throws IOException;
}
