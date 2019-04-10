package com.github.stupremee.mela.mongo.query;

import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.04.2019
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class Queries {

  private Queries() {
  }

  /**
   * Creates a new {@link Query} that matches documents where the {@literal _id} matches the given
   * value.
   *
   * @param value The value that field should have
   * @param <ValueT> The type of the value
   * @return The {@link Query} that matches all documents where the {@literal _id} has the given
   *     value
   */
  @Nonnull
  public static <ValueT> Query eq(ValueT value) {
    return eq("_id", value);
  }

  /**
   * Creates a {@link Query} that matches documents where the value of the given field equals the
   * given value.
   *
   * @param field The name of the field as a {@link String}
   * @param value The value that field should have
   * @param <ValueT> The type of the value
   * @return The {@link Query} that matches all documents where the field has the given value
   */
  @Nonnull
  public static <ValueT> Query eq(String field, ValueT value) {
    return new KeyValueQuery<>(field, value);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of the given field is
   * greater than the given value.
   *
   * @param field the field name
   * @param value the value
   * @param <ValueT> the value type
   * @return the filter
   */
  @Nonnull
  public static <ValueT> Query gt(String field, ValueT value) {
    return new OperatorQuery<>("$gt", field, value);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of the given field is
   * greater than or equal to the given value.
   *
   * @param field the field name
   * @param value the value
   * @param <ValueT> the value type
   * @return the filter
   */
  @Nonnull
  public static <ValueT> Query gte(String field, ValueT value) {
    return new OperatorQuery<>("$gte", field, value);
  }
}
