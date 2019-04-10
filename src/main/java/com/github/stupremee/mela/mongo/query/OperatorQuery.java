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
public class OperatorQuery<ValueT> implements Query {

  private final ValueT value;
  private final String field;
  private final String operator;

  OperatorQuery(String operator, String field, ValueT value) {
    this.field = field;
    this.operator = operator;
    this.value = value;
  }

  @Override
  public Bson toBson(@Nonnull CodecRegistry codecRegistry) {
    Preconditions.checkNotNull(codecRegistry, "codecRegistry can't be null.");
    return DocumentWriter.create(codecRegistry)
        .name(field)
        .startDocument()
        .name(operator)
        .value(value)
        .endDocument()
        .write();
  }
}
