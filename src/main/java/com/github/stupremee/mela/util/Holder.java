package com.github.stupremee.mela.util;

import io.vavr.control.Option;
import java.io.Serializable;
import org.jetbrains.annotations.Nullable;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
@SuppressWarnings("unused")
public final class Holder<T> implements Option<T>, Serializable {

  private static final long serialVersionUID = 2397316429611508241L;

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
  public void setValue(T value) {
    this.value = value;
  }

  /**
   * Returns the value that is currently stored in the holder.
   *
   * @return The value
   */
  @Nullable
  public T getValue() {
    return value;
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
