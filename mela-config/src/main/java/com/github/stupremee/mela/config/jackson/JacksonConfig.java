package com.github.stupremee.mela.config.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
final class JacksonConfig implements Config {

  private final JsonNode node;
  private final ObjectMapper mapper;

  JacksonConfig(JsonNode node, ObjectMapper mapper) {
    this.node = node;
    this.mapper = mapper;
  }

  @Override
  public <T> Optional<T> getAs(String path, Class<T> type) {
    return getNodeAtPath(path)
        .map(node -> {
          try {
            return mapper.treeToValue(node, type);
          } catch (JsonProcessingException cause) {
            throw new RuntimeException(cause);
          }
        });
  }

  @Override
  public Optional<String> getString(String path) {
    return getNodeAtPath(path)
        .filter(JsonNode::isTextual)
        .map(JsonNode::asText);
  }

  @Override
  public Collection<String> getStringList(String path) {
    return getListAtPath(path)
        .map(JsonNode::asText)
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
        .filter(JsonNode::canConvertToInt)
        .map(JsonNode::asInt);
    return number.isEmpty() ? OptionalInt.empty() : OptionalInt.of(number.get());
  }

  @Override
  public Collection<Integer> getIntegerList(String path) {
    return getListAtPath(path)
        .filter(JsonNode::canConvertToInt)
        .map(JsonNode::asInt)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public OptionalDouble getDouble(String path) {
    Optional<Double> number = getNodeAtPath(path)
        .filter(JsonNode::isDouble)
        .map(JsonNode::asDouble);
    return number.isEmpty() ? OptionalDouble.empty() : OptionalDouble.of(number.get());
  }

  @Override
  public Collection<Double> getDoubleList(String path) {
    return getListAtPath(path)
        .filter(JsonNode::isDouble)
        .map(JsonNode::asDouble)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public OptionalLong getLong(String path) {
    Optional<Long> number = getNodeAtPath(path)
        .filter(JsonNode::canConvertToLong)
        .map(JsonNode::asLong);
    return number.isEmpty() ? OptionalLong.empty() : OptionalLong.of(number.get());
  }

  @Override
  public Collection<Long> getLongList(String path) {
    return getListAtPath(path)
        .filter(JsonNode::canConvertToLong)
        .map(JsonNode::asLong)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public Optional<BigInteger> getBigInteger(String path) {
    return getNodeAtPath(path)
        .map(JsonNode::bigIntegerValue)
        .filter(number -> !number.equals(BigInteger.ZERO));
  }

  @Override
  public Collection<BigInteger> getBigIntegerList(String path) {
    return getListAtPath(path)
        .map(JsonNode::bigIntegerValue)
        .filter(number -> !number.equals(BigInteger.ZERO))
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public Optional<BigDecimal> getBigDecimal(String path) {
    return getNodeAtPath(path)
        .map(JsonNode::decimalValue)
        .filter(number -> !number.equals(BigDecimal.ZERO));
  }

  @Override
  public Collection<BigDecimal> getBigDecimalList(String path) {
    return getListAtPath(path)
        .map(JsonNode::decimalValue)
        .filter(number -> !number.equals(BigDecimal.ZERO))
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
