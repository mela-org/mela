package com.github.stupremee.mela.mongo.beans;

import com.github.stupremee.mela.beans.Bean;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.UUID;
import javax.annotation.Nonnull;
import org.bson.Document;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 07.04.2019
 */
@SuppressWarnings("unused")
public class MongoCollectionBean implements Bean {

  private static final long serialVersionUID = -8555736335162519651L;

  private String name;
  private UUID uuid;
  private boolean readOnly;

  public MongoCollectionBean() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this.getClass())
        .add("name", name)
        .add("uuid", uuid)
        .add("readOnly", readOnly)
        .toString();
  }

  /**
   * Creates a new {@link MongoCollectionBean} from a {@link Document} that was given by {@link
   * com.mongodb.reactivestreams.client.MongoClient#listDatabases()}.
   *
   * @param doc The {@link Document} to get the values from
   * @return The {@link MongoCollectionBean}
   */
  public static MongoCollectionBean ofDocument(@Nonnull Document doc) {
    Preconditions.checkNotNull(doc, "doc can't be null");

    var bean = new MongoCollectionBean();
    bean.setName(Preconditions.checkNotNull(doc.getString("name")));
    var infoDoc = Preconditions.checkNotNull(doc.get("info", Document.class));
    bean.setReadOnly(Preconditions.checkNotNull(infoDoc.getBoolean("readOnly")));
    bean.setUuid(Preconditions.checkNotNull(infoDoc.get("uuid", UUID.class)));
    return bean;
  }
}
