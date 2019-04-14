package com.github.stupremee.mela.mongo;

import org.bson.conversions.Bson;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.04.2019
 */
@FunctionalInterface
public interface Query {

  /**
   * Converts the {@link Query} to {@link Bson a bson} that can be used to query documents in
   * MongoDB.
   *
   * @return The {@link Bson} representing the {@link Query}
   */
  Bson asBson();
}
