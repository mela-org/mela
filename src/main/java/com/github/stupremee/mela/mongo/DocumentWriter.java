package com.github.stupremee.mela.mongo;

import com.mongodb.MongoClientSettings;
import java.util.ArrayList;
import java.util.List;
import org.bson.AbstractBsonWriter;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.BsonWriter;
import org.bson.codecs.Encoder;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * A class that makes it easier to write {@link BsonDocument documents} by using the {@link
 * BsonDocumentWriter}. It stores all steps in a list and writes all the steps in {@link #write()
 * the write method}.
 *
 * @author Stu
 * @since 10.04.2019
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class DocumentWriter {

  private final List<WriterStep> steps;
  private final CodecRegistry codecRegistry;

  private DocumentWriter(CodecRegistry codecRegistry) {
    this.steps = new ArrayList<>();
    this.codecRegistry = codecRegistry;
  }

  /**
   * Adds {@link BsonWriter#writeStartArray()} to the list of steps.
   *
   * @return The {@link DocumentWriter instance} for chained calls
   */
  public DocumentWriter startArray() {
    steps.add(AbstractBsonWriter::writeStartArray);
    return this;
  }

  /**
   * Adds {@link BsonWriter#writeEndArray()} to the list of steps.
   *
   * @return The {@link DocumentWriter instance} for chained calls
   */
  public DocumentWriter endArray() {
    steps.add(AbstractBsonWriter::writeEndArray);
    return this;
  }

  /**
   * Adds {@link BsonWriter#writeStartDocument()} to the list of steps.
   *
   * @return The {@link DocumentWriter instance} for chained calls
   */
  public DocumentWriter startDocument() {
    steps.add(AbstractBsonWriter::writeStartDocument);
    return this;
  }

  /**
   * Adds {@link BsonWriter#writeEndDocument()} to the list of steps.
   *
   * @return The {@link DocumentWriter instance} for chained calls
   */
  public DocumentWriter endDocument() {
    steps.add(AbstractBsonWriter::writeEndDocument);
    return this;
  }

  /**
   * Calls {@link #value(Object)} for each value in the values iterable.
   *
   * @param values The values that should be written to the document
   * @return The {@link DocumentWriter instance} for chained calls
   */
  public DocumentWriter values(Iterable<?> values) {
    values.forEach(this::value);
    return this;
  }

  /**
   * Encodes the value by using {@link #encodeValue(BsonDocumentWriter, Object, CodecRegistry)}.
   *
   * @param value The value that should be written to the document
   * @return The {@link DocumentWriter instance} for chained calls
   */
  public DocumentWriter value(Object value) {
    steps.add(writer -> encodeValue(writer, value, codecRegistry));
    return this;
  }

  /**
   * Adds {@link BsonWriter#writeName(String)} to the list of steps.
   *
   * @return The {@link DocumentWriter instance} for chained calls
   */
  public DocumentWriter name(String name) {
    steps.add(writer -> writer.writeName(name));
    return this;
  }

  /**
   * Writes all the steps in the steps list to a writer and then returns the document.
   *
   * @return The document
   */
  public BsonDocument write() {
    BsonDocumentWriter writer = new BsonDocumentWriter(new BsonDocument());
    writer.writeStartDocument();
    steps.forEach(step -> step.write(writer));
    writer.writeEndDocument();
    return writer.getDocument();
  }

  /**
   * This method was copied from the source code of the MongoDB Java Driver and can be found <a
   * href="https://github.com/mongodb/mongo-java-driver/blob/master/driver-core/src/main/com/mongodb/client/model/BuildersHelper.java#L29-L39">here</a>.
   */
  @SuppressWarnings("unchecked")
  private static void encodeValue(BsonDocumentWriter writer, Object value,
      CodecRegistry codecRegistry) {
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

  private interface WriterStep {

    void write(BsonDocumentWriter writer);
  }

  /**
   * Creates a new {@link DocumentWriter} with the default {@link CodecRegistry}.
   *
   * @return The {@link DocumentWriter}
   */
  public static DocumentWriter create() {
    return new DocumentWriter(MongoClientSettings.getDefaultCodecRegistry());
  }

  /**
   * Creates a new {@link DocumentWriter} that will use the given {@link CodecRegistry} to encode
   * values.
   *
   * @param codecRegistry The {@link CodecRegistry}
   * @return The {@link DocumentWriter}
   */
  public static DocumentWriter create(CodecRegistry codecRegistry) {
    return new DocumentWriter(codecRegistry);
  }
}
