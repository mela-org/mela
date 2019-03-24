package com.github.stupremee.mela.configuration;

import io.vavr.control.Option;
import java.util.Collection;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

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
  public Configuration section(String path) {
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
  public Collection<?> list(String path) {
    return Collections.emptyList();
  }

  @Override
  public boolean exists(String path) {
    return false;
  }

  @NotNull
  @Override
  public Option<Object> object(String path, Object def) {
    return Option.none();
  }

  @NotNull
  @Override
  public Collection<Object> objects(String path) {
    return Collections.emptyList();
  }

  @NotNull
  @Override
  public Option<String> string(String path, String def) {
    return Option.none();
  }

  @NotNull
  @Override
  public Collection<String> strings(String path) {
    return Collections.emptyList();
  }

  @NotNull
  @Override
  public Option<Number> number(String path, Number def) {
    return Option.none();
  }

  @NotNull
  @Override
  public Collection<Number> numbers(String path) {
    return Collections.emptyList();
  }


  @NotNull
  @Override
  public Option<Boolean> bool(String path, Boolean def) {
    return Option.none();
  }

  @NotNull
  @Override
  public Collection<Boolean> bools(String path) {
    return Collections.emptyList();
  }

  @NotNull
  @Override
  public Collection<String> keys() {
    return Collections.emptyList();
  }

  @Override
  public void remove(String path) {
    throw new UnsupportedOperationException("remove is not supported in an EmptyConfiguration.");
  }

  @Override
  public <T> Option<T> defaultValue(String path) {
    return Option.none();
  }

}
