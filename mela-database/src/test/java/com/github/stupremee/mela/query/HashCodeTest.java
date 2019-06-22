package com.github.stupremee.mela.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author Stu <https://github.com/Stupremee>
 * @since 16.06.19
 */
@SuppressWarnings("WeakerAccess")
public class HashCodeTest {

  @Test
  public void twoQueriesMatchHashCode() {
    Query one = Query.where("someKey").equalTo("hello");
    Query two = Query.where("someKey").equalTo("hello");

    assertThat(one).isEqualTo(two);
  }

  @Test
  public void twoQueriesNotMatchHashCode() {
    Query one = Query.where("someKey").equalTo("hello");
    Query two = Query.where("someKey").equalTo("hellol");

    assertThat(one).isNotEqualTo(two);
  }

}
