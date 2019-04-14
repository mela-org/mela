package com.github.stupremee.mela.mongo;

import com.google.common.base.Preconditions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.TextSearchOptions;
import com.mongodb.client.model.geojson.Geometry;
import io.vavr.collection.Stream;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bson.BsonType;
import org.bson.conversions.Bson;

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
   * @return The {@link Query}
   */
  @Nonnull
  public static <ValueT> Query eq(@Nullable ValueT value) {
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
  public static <ValueT> Query eq(@Nonnull String field, @Nullable ValueT value) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.eq(field, value);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of a field is not equal to
   * the given value.
   *
   * @param field The field name
   * @param value The value
   * @param <ValueT> The value type
   * @return The {@link Query}
   */
  @Nonnull
  public static <ValueT> Query ne(@Nonnull String field, @Nullable ValueT value) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.ne(field, value);
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
  public static <ValueT> Query gt(@Nonnull String field, @Nullable ValueT value) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.gt(field, value);
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
  public static <ValueT> Query gte(@Nonnull String field, @Nullable ValueT value) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.gte(field, value);
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
  @Nonnull
  @SafeVarargs
  public static <ValueT> Query in(@Nonnull String field, ValueT... values) {
    Preconditions.checkNotNull(field, "false can't be null.");
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
  @Nonnull
  public static <ValueT> Query in(@Nonnull String field, @Nonnull Iterable<ValueT> values) {
    Preconditions.checkNotNull(field, "field can't be null.");
    Preconditions.checkNotNull(values, "values can't be null.");
    return () -> Filters.in(field, values);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of a field does not equal
   * any of the specified values or does not exist.
   *
   * @param field The field name
   * @param values The list of values
   * @param <ValueT> The value type
   * @return The {@link Query}
   */
  @Nonnull
  @SafeVarargs
  public static <ValueT> Query nin(@Nonnull String field, ValueT... values) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return nin(field, Arrays.asList(values));
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of a field does not equal
   * any of the specified values or does not exist.
   *
   * @param field The field name
   * @param values The list of values
   * @param <ValueT> The value type
   * @return The {@link Query}
   */
  @Nonnull
  public static <ValueT> Query nin(@Nonnull String field, @Nonnull Iterable<ValueT> values) {
    Preconditions.checkNotNull(field, "field can't be null.");
    Preconditions.checkNotNull(values, "values can't be null.");
    return () -> Filters.nin(field, values);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of a field is less than the
   * given value.
   *
   * @param field The field name
   * @param value The value
   * @param <ValueT> The value type
   * @return The {@link Query}
   */
  @Nonnull
  public static <ValueT> Query lt(@Nonnull String field, @Nullable ValueT value) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.lt(field, value);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of a field is less than or
   * equal to the given value.
   *
   * @param field The field name
   * @param value The value
   * @param <ValueT> The value type
   * @return The {@link Query}
   */
  @Nonnull
  public static <ValueT> Query lte(@Nonnull String field, @Nullable ValueT value) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.lte(field, value);
  }

  /**
   * Creates a {@link Query} that performs a logical AND of the provided list of queries.
   *
   * @param queries The list of {@link Query Queries}
   * @return The {@link Query}
   */
  @Nonnull
  public static Query and(@Nonnull Iterable<Query> queries) {
    Preconditions.checkNotNull(queries, "queries can't be null.");
    return () -> Filters.and(Stream.ofAll(queries)
        .map(Query::toBson)
        .toJavaList());
  }

  /**
   * Creates a {@link Query} that performs a logical AND of the provided list of queries.
   *
   * @param queries The list of {@link Query Queries}
   * @return The {@link Query}
   */
  @Nonnull
  public static Query and(@Nonnull Query... queries) {
    Preconditions.checkNotNull(queries, "queries can't be null.");
    return and(Arrays.asList(queries));
  }

  /**
   * Creates a {@link Query} that matches all documents that do not match the passed in query.
   *
   * @param query The query that should be negated
   * @return The {@link Query}
   */
  @Nonnull
  public static Query not(@Nonnull Query query) {
    Preconditions.checkNotNull(query, "query can't be null.");
    return () -> Filters.not(query.toBson());
  }

  /**
   * Creates a {@link Query} that performs a logical NOR of the provided list of queries.
   *
   * @param queries The list of {@link Query Queries}
   * @return The {@link Query}
   */
  @Nonnull
  public static Query nor(@Nonnull Iterable<Query> queries) {
    Preconditions.checkNotNull(queries, "queries can't be null.");
    return () -> Filters.nor(Stream.ofAll(queries)
        .map(Query::toBson)
        .toJavaList());
  }

  /**
   * Creates a {@link Query} that performs a logical NOR of the provided list of queries.
   *
   * @param queries The list of {@link Query Queries}
   * @return The {@link Query}
   */
  @Nonnull
  public static Query nor(@Nonnull Query... queries) {
    Preconditions.checkNotNull(queries, "queries can't be null.");
    return nor(Arrays.asList(queries));
  }

  /**
   * Creates a {@link Query} that performs a logical OR of the provided list of queries.
   *
   * @param queries The list of {@link Query Queries}
   * @return The {@link Query}
   */
  @Nonnull
  public static Query or(@Nonnull Iterable<Query> queries) {
    Preconditions.checkNotNull(queries, "queries can't be null.");
    return () -> Filters.or(Stream.ofAll(queries)
        .map(Query::toBson)
        .toJavaList());
  }

  /**
   * Creates a {@link Query} that performs a logical OR of the provided list of queries.
   *
   * @param queries The list of {@link Query Queries}
   * @return The {@link Query}
   */
  @Nonnull
  public static Query or(@Nonnull Query... queries) {
    Preconditions.checkNotNull(queries, "queries can't be null.");
    return or(Arrays.asList(queries));
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of a field is an array that
   * contains all the specified values.
   *
   * @param <ValueT> The type of the values
   * @param field The field name
   * @param values The list of values
   * @return The {@link Query}
   */
  @Nonnull
  public static <ValueT> Query all(@Nonnull String field, @Nonnull Iterable<ValueT> values) {
    Preconditions.checkNotNull(field, "queries can't be null.");
    Preconditions.checkNotNull(values, "values can't be null.");
    return () -> Filters.all(field, values);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of a field is an array that
   * contains all the specified values.
   *
   * @param <ValueT> The type of the values
   * @param field The field name
   * @param values The list of values
   * @return The {@link Query}
   */
  @SafeVarargs
  @Nonnull
  public static <ValueT> Query all(@Nonnull String field, ValueT... values) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return all(field, Arrays.asList(values));
  }

  /**
   * Creates a {@link Query} that matches all documents that contain the given field.
   *
   * @param field The field name
   * @return The {@link Query}
   */
  @Nonnull
  public static Query exists(@Nonnull String field) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return exists(field, true);
  }

  /**
   * Creates a {@link Query} that matches all documents that contain the given field.
   *
   * @param field The field name
   * @param exists {@code true} to check for existence, {@code false} to check for absence
   * @return The {@link Query}
   */
  @Nonnull
  public static Query exists(@Nonnull String field, boolean exists) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.exists(field, exists);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of the field is of the
   * specified BSON type.
   *
   * @param field The field name
   * @param type The BSON type
   * @return The {@link Query}
   */
  @Nonnull
  public static Query type(@Nonnull String field, @Nonnull BsonType type) {
    Preconditions.checkNotNull(field, "field can't be null.");
    Preconditions.checkNotNull(type, "type can't be null.");
    return () -> Filters.type(field, type);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of the field is of the
   * specified BSON type.
   *
   * @param field The field name
   * @param type The string representation of the BSON type
   * @return The {@link Query}
   */
  @Nonnull
  public static Query type(@Nonnull String field, @Nonnull String type) {
    Preconditions.checkNotNull(field, "field can't be null.");
    Preconditions.checkNotNull(type, "type can't be null.");
    return () -> Filters.type(field, type);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of the field is of the
   * specified BSON type.
   *
   * @param field The field name
   * @param divisor The modulus
   * @param remainder The remainder
   * @return The {@link Query}
   */
  @Nonnull
  public static Query mod(@Nonnull String field, long divisor, long remainder) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.mod(field, divisor, remainder);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of the field matches the
   * given regular expression pattern with the given options applied.
   *
   * @param field The field name
   * @param pattern The RegEx pattern
   * @return The {@link Query}
   */
  @Nonnull
  public static Query regex(@Nonnull String field, @Nonnull String pattern) {
    Preconditions.checkNotNull(field, "field can't be null.");
    Preconditions.checkNotNull(pattern, "pattern can't be null.");
    return regex(field, pattern, null);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of the field matches the
   * given regular expression pattern with the given options applied.
   *
   * @param field The field name
   * @param pattern The RegEx pattern
   * @param options The options
   * @return The {@link Query}
   */
  @Nonnull
  public static Query regex(@Nonnull String field, @Nonnull String pattern,
      String options) {
    Preconditions.checkNotNull(field, "field can't be null.");
    Preconditions.checkNotNull(pattern, "pattern can't be null.");
    return () -> Filters.regex(field, pattern, options);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of the field matches the
   * given regular expression pattern with the given options applied.
   *
   * @param field The field name
   * @param pattern The RegEx pattern
   * @return The {@link Query}
   */
  @Nonnull
  public static Query regex(@Nonnull String field, @Nonnull Pattern pattern) {
    Preconditions.checkNotNull(field, "field can't be null.");
    Preconditions.checkNotNull(pattern, "pattern can't be null.");
    return () -> Filters.regex(field, pattern);
  }

  /**
   * Creates a {@link Query} that matches all documents matching the given search term.
   *
   * @param search The search term
   * @return The {@link Query}
   */
  @Nonnull
  public static Query text(@Nonnull String search) {
    Preconditions.checkNotNull(search, "search can't be null.");
    return text(search, new TextSearchOptions());
  }

  /**
   * Creates a {@link Query} that matches all documents matching the given search term.
   *
   * @param search The search term
   * @return The {@link Query}
   */
  @Nonnull
  public static Query text(@Nonnull String search, @Nonnull TextSearchOptions options) {
    Preconditions.checkNotNull(search, "search can't be null.");
    Preconditions.checkNotNull(options, "options can't be null.");
    return () -> Filters.text(search, options);
  }

  /**
   * Creates a {@link Query} that matches all documents for which the given expression is true.
   *
   * @param expression Thr expression
   * @return The {@link Query}
   */
  @Nonnull
  public static Query where(@Nonnull String expression) {
    Preconditions.checkNotNull(expression, "expression can't be null.");
    return () -> Filters.where(expression);
  }

  /**
   * Creates a {@link Query} that matches all documents that validate against the given JSON schema
   * document.
   *
   * @param expression Thr expression type
   * @return The {@link Query}
   */
  @Nonnull
  public static <ExpressionT> Query expr(@Nonnull ExpressionT expression) {
    Preconditions.checkNotNull(expression, "expression can't be null.");
    return () -> Filters.expr(expression);
  }

  /**
   * Creates a {@link Query} that matches all documents containing a field that is an array where at
   * least one member of the array matches the given filter.
   *
   * @param field The field name
   * @param filter The filter to apply on each element
   * @return The {@link Query}
   */
  @Nonnull
  public static Query elemMatch(@Nonnull String field, @Nonnull Bson filter) {
    Preconditions.checkNotNull(field, "field can't be null.");
    Preconditions.checkNotNull(filter, "filter can't be null.");
    return () -> Filters.elemMatch(field, filter);
  }

  /**
   * Creates a {@link Query} that matches all documents where the value of a field is an array of
   * the specified size.
   *
   * @param field The field name
   * @param size The size of the array
   * @return The {@link Query}
   */
  @Nonnull
  public static Query size(@Nonnull String field, int size) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.size(field, size);
  }

  /**
   * Creates a {@link Query} that matches all documents where all of the bit positions are clear in
   * the field.
   *
   * @param field The field name
   * @param bitmask The bitmask
   * @return The {@link Query}
   */
  @Nonnull
  public static Query bitsAllClear(@Nonnull String field, long bitmask) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.bitsAllClear(field, bitmask);
  }

  /**
   * Creates a {@link Query} that matches all documents where any of the bit positions are clear in
   * the field.
   *
   * @param field The field name
   * @param bitmask The bitmask
   * @return The {@link Query}
   */
  @Nonnull
  public static Query bitsAnyClear(@Nonnull String field, long bitmask) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.bitsAnyClear(field, bitmask);
  }

  /**
   * Creates a {@link Query} that matches all documents where all of the bit positions are set in
   * the field.
   *
   * @param field The field name
   * @param bitmask The bitmask
   * @return The {@link Query}
   */
  @Nonnull
  public static Query bitsAllSet(@Nonnull String field, long bitmask) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.bitsAllSet(field, bitmask);
  }

  /**
   * Creates a {@link Query} that matches all documents where any of the bit positions are set in
   * the field.
   *
   * @param field The field name
   * @param bitmask The bitmask
   * @return The {@link Query}
   */
  @Nonnull
  public static Query bitsAnySet(@Nonnull String field, long bitmask) {
    Preconditions.checkNotNull(field, "field can't be null.");
    return () -> Filters.bitsAnySet(field, bitmask);
  }

  /**
   * Creates a {@link Query} that matches all documents containing a field with geospatial data that
   * exists entirely within the specified shape.
   *
   * @param field The field name
   * @param geometry The bounding GeoJSON geometry object
   * @return The {@link Query}
   */
  @Nonnull
  public static Query geoWithin(@Nonnull String field, @Nonnull Geometry geometry) {
    Preconditions.checkNotNull(field, "field can't be null.");
    Preconditions.checkNotNull(geometry, "geometry can't be null.");
    return () -> Filters.geoWithin(field, geometry);
  }

  /**
   * Creates a {@link Query} that matches all documents containing a field with geospatial data that
   * exists entirely within the specified shape.
   *
   * @param field The field name
   * @param geometry The bounding GeoJSON geometry object
   * @return The {@link Query}
   */
  @Nonnull
  public static Query geoWithin(@Nonnull String field, @Nonnull Bson geometry) {
    Preconditions.checkNotNull(field, "field can't be null.");
    Preconditions.checkNotNull(geometry, "geometry can't be null.");
    return () -> Filters.geoWithin(field, geometry);
  }
}
