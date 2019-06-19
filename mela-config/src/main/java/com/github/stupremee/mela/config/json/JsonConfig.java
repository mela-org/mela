package com.github.stupremee.mela.config.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.stupremee.mela.config.Config;
import com.google.common.collect.Streams;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 19.06.19
 */
final class JsonConfig implements Config {

  private final JsonNode node;

  JsonConfig(JsonNode node) {
    this.node = node;
  }

  @Override
  public Optional<String> getString(String path) {
    return getNodeAtPath(path)
        .filter(JsonNode::isTextual)
        .map(JsonNode::textValue);
  }

  @Override
  public Collection<String> getStringList(String path) {
    return getListAtPath(path)
        .filter(JsonNode::isTextual)
        .map(JsonNode::textValue)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public Optional<Number> getNumber(String path) {
    return getNodeAtPath(path)
        .filter(JsonNode::isNumber)
        .map(JsonNode::numberValue);
  }

  @Override
  public Collection<Number> getNumberList(String path) {
    return getListAtPath(path)
        .filter(JsonNode::isNumber)
        .map(JsonNode::numberValue)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public OptionalInt getInteger(String path) {
    Optional<Integer> number = getNodeAtPath(path)
        .filter(JsonNode::isInt)
        .map(JsonNode::intValue);
    return number.isEmpty() ? OptionalInt.empty() : OptionalInt.of(number.get());
  }

  @Override
  public Collection<Integer> getIntegerList(String path) {
    return getListAtPath(path)
        .filter(JsonNode::isInt)
        .map(JsonNode::intValue)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public OptionalDouble getDouble(String path) {
    Optional<Double> number = getNodeAtPath(path)
        .filter(JsonNode::isDouble)
        .map(JsonNode::doubleValue);
    return number.isEmpty() ? OptionalDouble.empty() : OptionalDouble.of(number.get());
  }

  @Override
  public Collection<Double> getDoubleList(String path) {
    return getListAtPath(path)
        .filter(JsonNode::isDouble)
        .map(JsonNode::doubleValue)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public OptionalLong getLong(String path) {
    Optional<Long> number = getNodeAtPath(path)
        .filter(JsonNode::isLong)
        .map(JsonNode::longValue);
    return number.isEmpty() ? OptionalLong.empty() : OptionalLong.of(number.get());
  }

  @Override
  public Collection<Long> getLongList(String path) {
    return getListAtPath(path)
        .filter(JsonNode::isLong)
        .map(JsonNode::longValue)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public Optional<BigInteger> getBigInteger(String path) {
    return getNodeAtPath(path)
        .filter(JsonNode::isBigInteger)
        .map(JsonNode::bigIntegerValue);
  }

  @Override
  public Collection<BigInteger> getBigIntegerList(String path) {
    return getListAtPath(path)
        .filter(JsonNode::isBigInteger)
        .map(JsonNode::bigIntegerValue)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public Optional<BigDecimal> getBigDecimal(String path) {
    return getNodeAtPath(path)
        .filter(JsonNode::isBigDecimal)
        .map(JsonNode::decimalValue);
  }

  @Override
  public Collection<BigDecimal> getBigDecimalList(String path) {
    return getListAtPath(path)
        .filter(JsonNode::isBigDecimal)
        .map(JsonNode::decimalValue)
        .collect(Collectors.toUnmodifiableList());
  }

  private Stream<JsonNode> getListAtPath(String fullPath) {
    Optional<JsonNode> node = getNodeAtPath(fullPath);
    if (node.isEmpty()) {
      return Stream.empty();
    } else {
      //noinspection UnstableApiUsage
      return Streams.stream(node.get().elements());
    }
  }

  private Optional<JsonNode> getNodeAtPath(String fullPath) {
    String[] paths = fullPath.split("\\.");
    JsonNode result = null;
    for (String part : paths) {
      result = result == null ? node.get(part) : result.get(part);
    }
    return Optional.ofNullable(result);
  }
}
