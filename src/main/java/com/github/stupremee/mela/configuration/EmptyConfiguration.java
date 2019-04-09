package com.github.stupremee.mela.configuration;

import io.vavr.control.Option;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

  @Nonnull
  @Override
  public Configuration getSection(@Nonnull String path) {
    return new EmptyConfiguration();
  }

  @Override
  public void set(@Nonnull String path, @Nonnull Object value) {
    throw new UnsupportedOperationException("set is not supported in an EmptyConfiguration.");
  }

  @Nonnull
  @Override
  public <T> Option<T> get(@Nonnull String path, @Nullable T def) {
    return Option.none();
  }

  @Nonnull
  @Override
  public Collection<?> getList(@Nonnull String path) {
    return Collections.emptyList();
  }

  @Override
  public boolean exists(@Nonnull String path) {
    return false;
  }

  @Nonnull
  @Override
  public Option<Object> getObject(@Nonnull String path, @Nullable Object def) {
    return Option.none();
  }

  @Nonnull
  @Override
  public Collection<Object> getObjects(@Nonnull String path) {
    return Collections.emptyList();
  }

  @Nonnull
  @Override
  public Option<String> getString(@Nonnull String path, @Nullable String def) {
    return Option.none();
  }

  @Nonnull
  @Override
  public Collection<String> getStrings(@Nonnull String path) {
    return Collections.emptyList();
  }

  @Nonnull
  @Override
  public Option<Number> getNumber(@Nonnull String path, @Nullable Number def) {
    return Option.none();
  }

  @Nonnull
  @Override
  public Collection<Number> getNumbers(@Nonnull String path) {
    return Collections.emptyList();
  }


  @Nonnull
  @Override
  public Option<Boolean> getBool(@Nonnull String path, @Nullable Boolean def) {
    return Option.none();
  }

  @Nonnull
  @Override
  public Collection<Boolean> getBools(@Nonnull String path) {
    return Collections.emptyList();
  }

  @Nonnull
  @Override
  public Collection<String> getKeys() {
    return Collections.emptyList();
  }

  @Override
  public void remove(@Nonnull String path) {
    throw new UnsupportedOperationException("delete is not supported in an EmptyConfiguration.");
  }

  @Override
  public <T> Option<T> getDefaultValue(@Nonnull String path) {
    return Option.none();
  }


  @Nonnull
  @Override
  public String writeToString(@Nonnull ConfigurationParser parser) {
    // Do nothing
    return "";
  }

  @Override
  public void write(@Nonnull Writer writer, @Nonnull ConfigurationParser parser) {
    // Do nothing
  }

}
