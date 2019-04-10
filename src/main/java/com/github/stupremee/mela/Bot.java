package com.github.stupremee.mela;

import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 23.03.2019
 */
public class Bot {

  public static void main(String[] args) {
    print(Filters.or(Filters.eq("key"), Filters.exists("content")));
  }

  private static void print(Bson filter) {
    System.out
        .println(filter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry())
            .toJson(JsonWriterSettings.builder().indent(true).build()));
  }

}
