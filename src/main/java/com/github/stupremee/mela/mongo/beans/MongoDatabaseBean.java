package com.github.stupremee.mela.mongo.beans;

import com.github.stupremee.mela.beans.Bean;
import com.google.common.base.MoreObjects;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 07.04.2019
 */
@SuppressWarnings("unused")
public class MongoDatabaseBean implements Bean {

  private static final long serialVersionUID = 7753061786456967159L;
  private String name;
  private double sizeOnDisk;
  private boolean empty;

  public MongoDatabaseBean() {

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getSizeOnDisk() {
    return sizeOnDisk;
  }

  public void setSizeOnDisk(double sizeOnDisk) {
    this.sizeOnDisk = sizeOnDisk;
  }

  public boolean isEmpty() {
    return empty;
  }

  public void setEmpty(boolean empty) {
    this.empty = empty;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this.getClass())
        .add("name", name)
        .add("sizeOnDisk", sizeOnDisk)
        .add("empty", empty)
        .toString();
  }
}
