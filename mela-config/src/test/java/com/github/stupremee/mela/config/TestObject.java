package com.github.stupremee.mela.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

/**
 * @author Stu <https://github.com/Stupremee>
 * @since 22.06.19
 */
final class TestObject {

  @JsonProperty
  private final long id;
  @JsonProperty
  private final String name;
  @JsonProperty
  private final int count;

  @JsonCreator
  TestObject(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("count") int count) {
    this.id = id;
    this.name = name;
    this.count = count;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getCount() {
    return count;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, name, count);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (o == this) {
      return true;
    }

    if (!(o instanceof TestObject)) {
      return false;
    }

    TestObject other = (TestObject) o;
    return this.id == other.id
        && this.name.equals(other.name)
        && this.count == other.count;
  }
}
