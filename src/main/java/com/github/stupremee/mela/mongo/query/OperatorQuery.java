package com.github.stupremee.mela.mongo.query;

import com.google.common.base.Preconditions;
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
public class OperatorQuery<ElementT> implements Query {

  private final ElementT value;
  private final String field;
  private final String operator;

  private OperatorQuery(String field, String operator, ElementT value) {
    this.field = field;
    this.operator = operator;
    this.value = value;
  }

  @Override
  public Bson toBson(@Nonnull CodecRegistry codecRegistry) {
    Preconditions.checkNotNull(codecRegistry, "codecRegistry can't be null.");
    BsonDocumentWriter writer = new BsonDocumentWriter(new BsonDocument());

    writer.writeStartDocument();
    writer.writeName(field);
    writer.writeStartDocument();
    writer.writeName(operator);
    CriteriaHelpers.encodeValue(writer, value, codecRegistry);
    writer.writeEndDocument();
    writer.writeEndDocument();

    return writer.getDocument();
  }
}
