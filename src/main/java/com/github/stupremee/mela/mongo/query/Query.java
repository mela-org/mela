package com.github.stupremee.mela.mongo.query;

import com.mongodb.MongoClientSettings;
import javax.annotation.Nonnull;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.04.2019
 */
public interface Query {

  /**
   * Converts the {@link Query} by calling {@link #toBson(CodecRegistry)} with the default {@link
   * CodecRegistry} that comes from {@link MongoClientSettings#getDefaultCodecRegistry()}.
   *
   * @return The {@link Bson} representing the {@link Query}
   */
  default Bson toBson() {
    return toBson(MongoClientSettings.getDefaultCodecRegistry());
  }

  /**
   * Converts the Query to a {@link Bson Bson Filter} that can be used to find the object.
   *
   * @param codecRegistry The {@link CodecRegistry} that is used to encode values
   * @return The {@link Query} as a {@link Bson}
   */
  Bson toBson(@Nonnull CodecRegistry codecRegistry);

}
