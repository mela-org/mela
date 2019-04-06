package com.github.stupremee.mela.configuration;

import io.vavr.control.Option;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 23.03.2019
 */
final class EmptyConfiguration implements Configuration {

  private static class Lazy {

    static Configuration INSTANCE = new EmptyConfiguration();
  }

  static Configuration instance() {
    return Lazy.INSTANCE;
  }

  private EmptyConfiguration() {
  }

  @NotNull
  @Override
  public Configuration getSection(String path) {
    return new EmptyConfiguration();
  }

  @Override
  public void set(String path, Object value) {
    throw new UnsupportedOperationException("set is not supported in an EmptyConfiguration.");
  }

  @NotNull
  @Override
  public <T> Option<T> get(String path, T def) {
    return Option.none();
  }

  @NotNull
  @Override
  public Collection<?> getList(String path) {
    return Collections.emptyList();
  }

  @Override
  public boolean exists(String path) {
    return false;
  }

  @NotNull
  @Override
  public Option<Object> getObject(String path, Object def) {
    return Option.none();
  }

  @NotNull
  @Override
  public Collection<Object> getObjects(String path) {
    return Collections.emptyList();
  }

  @NotNull
  @Override
  public Option<String> getString(String path, String def) {
    return Option.none();
  }

  @NotNull
  @Override
  public Collection<String> getStrings(String path) {
    return Collections.emptyList();
  }

  @NotNull
  @Override
  public Option<Number> getNumber(String path, Number def) {
    return Option.none();
  }

  @NotNull
  @Override
  public Collection<Number> getNumbers(String path) {
    return Collections.emptyList();
  }


  @NotNull
  @Override
  public Option<Boolean> getBool(String path, Boolean def) {
    return Option.none();
  }

  @NotNull
  @Override
  public Collection<Boolean> getBools(String path) {
    return Collections.emptyList();
  }

  @NotNull
  @Override
  public Collection<String> getKeys() {
    return Collections.emptyList();
  }

  @Override
  public void remove(String path) {
    throw new UnsupportedOperationException("delete is not supported in an EmptyConfiguration.");
  }

  @Override
  public <T> Option<T> getDefaultValue(String path) {
    return Option.none();
  }


  @NotNull
  @Override
  public String writeToString(ConfigurationParser parser) {
    // Do nothing
    return "";
  }

  @Override
  public void write(Writer writer, ConfigurationParser parser) {
    // Do nothing
  }

}
