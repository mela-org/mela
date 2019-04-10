package com.github.stupremee.mela.mongo.query;

import com.github.stupremee.mela.mongo.DocumentWriter;
import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.04.2019
 */
public class IterableOperatorQuery<ValueT> implements Query {

  private final String operator;
  private final String field;
  private final Iterable<ValueT> values;

  IterableOperatorQuery(String operator, String field, Iterable<ValueT> values) {
    this.operator = operator;
    this.field = field;
    this.values = values;
  }

  @Override
  public Bson toBson(@Nonnull CodecRegistry codecRegistry) {
    Preconditions.checkNotNull(codecRegistry, "codecRegistry can't be null.");
    
    return DocumentWriter.create(codecRegistry)
        .name(field)
        .startDocument()
        .name(operator)
        .startArray()
        .values(values)
        .endArray()
        .endDocument()
        .write();
  }
}
