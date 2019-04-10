package com.github.stupremee.mela.mongo.query;

import com.github.stupremee.mela.mongo.DocumentWriter;
import com.google.common.base.Preconditions;
import io.vavr.collection.Stream;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.04.2019
 */
final class ConjunctionQuery implements Query {

  private final Iterable<Query> queries;

  ConjunctionQuery(Iterable<Query> queries) {
    this.queries = queries;
  }

  @Override
  public Bson toBson(@Nonnull CodecRegistry codecRegistry) {
    Preconditions.checkNotNull(codecRegistry, "codecRegistry can't be null.");

    return DocumentWriter.create(codecRegistry)
        .name("$and")
        .startArray()
        .values(mapQueries(queries, codecRegistry))
        .endArray()
        .write();
  }

  private Iterable<? extends Bson> mapQueries(Iterable<Query> queries,
      CodecRegistry codecRegistry) {
    // TODO: Map better to get a better query result
    return Stream.ofAll(queries)
        .map(query -> query.toBson(codecRegistry))
        .collect(Collectors.toList());
  }
}
