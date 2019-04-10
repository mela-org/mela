package com.github.stupremee.mela;

import com.github.stupremee.mela.mongo.query.Queries;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 23.03.2019
 */
public class Bot {

  public static void main(String[] args) {
    //.toBsonDocument(BsonDocument.class, MongoClientSettings.getDefaultCodecRegistry());
    var not = Queries.not(Queries.and(Queries.eq("one"), Queries.eq("two")));
    System.out.println(not.toBson());
  }

}
