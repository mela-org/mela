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
public class KeyValueQuery<ElementT> implements Query {

  private final ElementT value;
  private final String field;

  private KeyValueQuery(String field, ElementT value) {
    Preconditions.checkNotNull(field, "field can't be null.");
    Preconditions.checkNotNull(value, "value can't be null.");
    this.field = field;
    this.value = value;
  }

  @Override
  public Bson toBson(@Nonnull CodecRegistry codecRegistry) {
    Preconditions.checkNotNull(codecRegistry, "codecRegistry can't be null.");
    BsonDocumentWriter writer = new BsonDocumentWriter(new BsonDocument());

    writer.writeStartDocument();
    writer.writeName(field);
    CriteriaHelpers.encodeValue(writer, value, codecRegistry);
    writer.writeEndDocument();

    return writer.getDocument();
  }
}
