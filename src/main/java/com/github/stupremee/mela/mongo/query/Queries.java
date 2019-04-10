package com.github.stupremee.mela.mongo.query;

import java.util.Arrays;
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
   * @return The {@link Query}
   */
  @Nonnull
  public static <ValueT> Query eq(String field, ValueT value) {
    return new KeyValueQuery<>(field, value);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of the given field is
   * greater than the given value.
   *
   * @param field The field name
   * @param value The value
   * @param <ValueT> The value type
   * @return The filter
   */
  @Nonnull
  public static <ValueT> Query gt(String field, ValueT value) {
    return new OperatorQuery<>("$gt", field, value);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of the given field is
   * greater than or equal to the given value.
   *
   * @param field The field name
   * @param value The value
   * @param <ValueT> The value type
   * @return The filter
   */
  @Nonnull
  public static <ValueT> Query gte(String field, ValueT value) {
    return new OperatorQuery<>("$gte", field, value);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of a field equals any value
   * in the list of specified values.
   *
   * @param field The field name
   * @param values The list of values
   * @param <ValueT> The value type
   * @return The {@link Query}
   */
  @SafeVarargs
  public static <ValueT> Query in(String field, ValueT... values) {
    return in(field, Arrays.asList(values));
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of a field equals any value
   * in the list of specified values.
   *
   * @param field The field name
   * @param values The list of values
   * @param <ValueT> The value type
   * @return The {@link Query}
   */
  public static <ValueT> Query in(String field, Iterable<ValueT> values) {
    return new IterableOperatorQuery<>("$in", field, values);
  }
}
