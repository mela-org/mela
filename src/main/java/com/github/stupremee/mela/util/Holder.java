package com.github.stupremee.mela.util;

import com.google.common.base.Preconditions;
import io.vavr.control.Option;
import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
@SuppressWarnings("unused")
public class Holder<T> implements Option<T> {

  private T value;

  private Holder(T value) {
    this.value = value;
  }

  @Override
  public boolean isEmpty() {
    return value == null;
  }

  @Override
  public String stringPrefix() {
    return "Holder";
  }

  @Override
  public T get() {
    return value;
  }

  @Override
  public String toString() {
    return stringPrefix() + "(" + value + ")";
  }

  /**
   * Replaces the old value with the given value.
   *
   * @param value The value the holder should "hold"
   */
  public void set(@NotNull T value) {
    this.value = Preconditions.checkNotNull(value);
  }

  /**
   * Creates and returns an empty holder.
   *
   * @param <T> The type of the holder
   * @return The holder
   */
  public static <T> Holder<T> empty() {
    return new Holder<>(null);
  }

  /**
   * Creates and returns an holder of the given value.
   *
   * @param <T> The type of the holder
   * @param value The value the holder should "hold"
   * @return The holder
   */
  public static <T> Holder<T> of(T value) {
    return new Holder<>(value);
  }
}
