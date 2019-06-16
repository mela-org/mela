package com.github.stupremee.mela.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 16.06.19
 */
@SuppressWarnings("WeakerAccess")
public class EqualsTest {

  @Test
  public void twoQueriesShouldMatch() {
    Query one = Query.where("someKey").between(10, 20);
    Query two = Query.where("someKey").between(10, 20);

    assertThat(one).isEqualTo(two);
  }

  @Test
  public void twoQueriesShouldNotMatch() {
    Query one = Query.where("someKey").between(10, 20);
    Query two = Query.where("someKey").equalTo("hello");

    assertThat(one).isNotEqualTo(two);
  }

}