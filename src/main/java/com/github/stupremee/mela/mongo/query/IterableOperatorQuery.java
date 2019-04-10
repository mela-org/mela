package com.github.stupremee.mela.mongo.query;

import javax.annotation.Nonnull;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.04.2019
 */
public class IterableOperatorQuery<ValueT> implements Query {

  private final String operator;
  private final String field;
  private final Iterable<ValueT> values;

  IterableOperatorQuery(String operator, String field, Iterable<ValueT> values) {
    this.operator = operator;
    this.field = field;
    this.values = values;
  }

  @Override
  public Bson toBson(@Nonnull CodecRegistry codecRegistry) {
    BsonDocumentWriter writer = new BsonDocumentWriter(new BsonDocument());

    writer.writeStartDocument();
    writer.writeName(field);
    writer.writeStartDocument();
    writer.writeName(operator);
    writer.writeStartArray();
    values.forEach(value -> CriteriaHelpers.encodeValue(writer, value, codecRegistry));
    writer.writeEndArray();
    writer.writeEndDocument();
    writer.writeEndDocument();
    return writer.getDocument();
  }
}
