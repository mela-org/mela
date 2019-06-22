package com.github.stupremee.mela.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 22.06.19
 */
abstract class BasicConfigTest {

  private final Config config;

  BasicConfigTest(Config config) {
    this.config = config;
  }

  @Test
  void stringOperations() {
    assertThat(config.getString("someString").orElse(""))
        .isEqualTo("something");

    assertThat(config.getString("some.string").orElse(""))
        .isEqualTo("some");
  }

  @Test
  void stringListOperations() {
    assertThat(config.getStringList("someStrings"))
        .containsExactly("some", "strings", "are", "here");

    assertThat(config.getStringList("some.strings"))
        .containsExactly("some", "more", "strings");
  }

  @Test
  void numberOperations() {
    assertThat(config.getNumber("someNumber").orElse(null))
        .isEqualTo(1337);

    assertThat(config.getNumber("some.number").orElse(null))
        .isEqualTo(3173);
  }

  @Test
  void numberListOperations() {
    assertThat(config.getNumberList("someNumbers"))
        .containsExactly(1, 3, 5, 7);

    assertThat(config.getNumberList("some.numbers"))
        .containsExactly(2, 4, 6, 8);
  }

  @Test
  void primitivesOperations() {
    assertThat(config.getInteger("someInt").orElse(-1))
        .isEqualTo(787);

    assertThat(config.getInteger("some.int").orElse(-1))
        .isEqualTo(123);

    assertThat(config.getLong("someLong").orElse(-1))
        .isEqualTo(987654321);

    assertThat(config.getLong("some.long").orElse(-1))
        .isEqualTo(123456789);

    assertThat(config.getDouble("someDouble").orElse(-1))
        .isEqualTo(123.321);

    assertThat(config.getDouble("some.double").orElse(-1))
        .isEqualTo(321.123);
  }

  @Test
  void primitivesListOperations() {
    assertThat(config.getIntegerList("someInts"))
        .containsExactly(1, 2, 5, 6);

    assertThat(config.getIntegerList("some.ints"))
        .containsExactly(3, 4, 7, 8);

    assertThat(config.getLongList("someLongs"))
        .containsExactly(11111L, 22222L, 55555L, 66666L);

    assertThat(config.getLongList("some.longs"))
        .containsExactly(3333333333L, 4444444444L, 7777777777L, 8888888888L);

    assertThat(config.getDoubleList("someDoubles"))
        .containsExactly(1.1, 2.2, 5.5, 6.6);

    assertThat(config.getDoubleList("some.doubles"))
        .containsExactly(3.3, 4.4, 7.7, 8.8);
  }
}
