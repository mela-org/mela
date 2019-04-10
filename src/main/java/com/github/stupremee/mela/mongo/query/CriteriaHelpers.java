package com.github.stupremee.mela.mongo.query;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.codecs.Encoder;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * This class is just copied from the {@literal com.mongodb.client.model.BuilderHelpers}, because
 * the mongo class is package-private.
 *
 * @author Stu
 * @since 10.04.2019
 */
final class CriteriaHelpers {

  private CriteriaHelpers() {

  }

  @SuppressWarnings("unchecked")
  static <TItem> void encodeValue(final BsonDocumentWriter writer, final TItem value,
      final CodecRegistry codecRegistry) {
    if (value == null) {
      writer.writeNull();
    } else if (value instanceof Bson) {
      codecRegistry.get(BsonDocument.class).encode(writer,
          ((Bson) value).toBsonDocument(BsonDocument.class, codecRegistry),
          EncoderContext.builder().build());
    } else {
      ((Encoder) codecRegistry.get(value.getClass()))
          .encode(writer, value, EncoderContext.builder().build());
    }
  }
}
