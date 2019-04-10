package com.github.stupremee.mela.mongo.query;

import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.04.2019
 */
public class BsonQueryAdapter implements Bson {

  private final Query query;

  private BsonQueryAdapter(Query query) {
    this.query = query;
  }

  @Override
  public <TDocument> BsonDocument toBsonDocument(Class<TDocument> tDocumentClass,
      CodecRegistry codecRegistry) {
    return query.toBson(codecRegistry).toBsonDocument(tDocumentClass, codecRegistry);
  }

  public static BsonQueryAdapter of(Query query) {
    return new BsonQueryAdapter(query);
  }
}
