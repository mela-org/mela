package com.github.stupremee.mela.beans;

import org.bson.codecs.pojo.annotations.BsonId;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
public abstract class SnowflakeBean implements Bean {

  private static final long serialVersionUID = -2074915195340425431L;

  @BsonId
  private long id;

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

}
