package com.github.stupremee.mela.mongo.query;

import com.github.stupremee.mela.mongo.DocumentWriter;
import javax.annotation.Nonnull;
import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.04.2019
 */
final class NotQuery implements Query {

  private final Query query;

  NotQuery(Query query) {
    this.query = query;
  }

  @Override
  public Bson toBson(@Nonnull CodecRegistry codecRegistry) {
    return DocumentWriter.create(codecRegistry)
        .name(getFieldName(query, codecRegistry))
        .startDocument()
        .name("$not")
        .value(BsonQueryAdapter.of(query))
        .endDocument()
        .write();
  }

  private String getFieldName(Query query, CodecRegistry codecRegistry) {
    return query
        .toBson(codecRegistry)
        .toBsonDocument(BsonDocument.class, codecRegistry)
        .entrySet().iterator().next().getKey();
  }
}
