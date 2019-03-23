package com.github.stupremee.mela.configuration;

import io.vavr.control.Option;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 23.03.2019
 */
public class TomlConfiguration implements Configuration {

  private final Configuration defaults;
  private final Map<String, Object> map;

  TomlConfiguration(LinkedHashMap<String, Object> map, Configuration defaults) {
    this.defaults = defaults;
    this.map = map;
  }

  @NotNull
  @Override
  public Configuration section(String path) {
    return null;
  }

  @NotNull
  @Override
  public <T> Option<T> get(String path, T def) {
    return null;
  }

  @NotNull
  @Override
  public Collection<Object> list(String path, Collection<?> def) {
    return null;
  }

  @Override
  public boolean exists(String path) {
    return !get(path, null).isEmpty();
  }

  @NotNull
  @Override
  public Option<Object> object(String path, Object def) {
    return null;
  }

  @NotNull
  @Override
  public Collection<Object> objects(String path, Collection<Object> def) {
    return null;
  }

  @NotNull
  @Override
  public Option<String> string(String path, String def) {
    return null;
  }

  @NotNull
  @Override
  public Collection<String> strings(String path, Collection<String> def) {
    return null;
  }

  @NotNull
  @Override
  public Option<Number> number(String path, Number def) {
    return null;
  }

  @NotNull
  @Override
  public Collection<Number> numbers(String path, Collection<Number> def) {
    return null;
  }

  @NotNull
  @Override
  public Option<Boolean> bool(String path, Boolean def) {
    return null;
  }

  @NotNull
  @Override
  public Collection<Boolean> bools(String path, Collection<Boolean> def) {
    return null;
  }
}
