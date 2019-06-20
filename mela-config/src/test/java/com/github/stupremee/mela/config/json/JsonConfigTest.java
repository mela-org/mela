package com.github.stupremee.mela.config.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.stupremee.mela.config.Config;
import com.github.stupremee.mela.config.ConfigProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 19.06.19
 */
@SuppressWarnings("WeakerAccess")
public final class JsonConfigTest {

  Injector injector;
  ConfigProvider configProvider;

  @BeforeEach
  public void setup() {
    this.injector = Guice.createInjector(JsonConfigModule.create());
    this.configProvider = injector.getInstance(ConfigProvider.class);
  }

  @AfterEach
  public void tearDown() {
    this.injector = null;
    this.configProvider = null;
  }

  @Test
  public void basicConfigOperations() {
    String basicConfig = "{\"someString\":\"hello guys\",\"someNumber\":1337,\"someObject\":{\"someString\":\"hey\"}}";

    Config config = configProvider.load(basicConfig);

    assertThat(config.getString("someString").orElse(""))
        .isEqualTo("hello guys");

    assertThat(config.getNumber("someNumber").orElse(null))
        .isEqualTo(1337);

    assertThat(config.getString("someObject.someString").orElse(null))
        .isEqualTo("hey");
  }

  @Test
  public void basicConfigListOperations() {
    String basicConfig = "{\"stringList\":[\"hello\",\"hey\",\"hi\",\"omg\",\"testing\"],\"numberList\":[1,2,3,5,8],\"doubleList\":[1.0,59.1,7.7,13.37]}";

    Config config = configProvider.load(basicConfig);

    assertThat(config.getStringList("stringList"))
        .containsExactly("hello", "hey", "hi", "omg", "testing");

    assertThat(config.getNumberList("numberList"))
        .containsExactly(1, 2, 3, 5, 8);

    assertThat(config.getLongList("numberList"))
        .containsExactly(1L, 2L, 3L, 5L, 8L);

    assertThat(config.getIntegerList("numberList"))
        .containsExactly(1, 2, 3, 5, 8);

    assertThat(config.getDoubleList("doubleList"))
        .containsExactly(1.0, 59.1, 7.7, 13.37);
  }

  @Test
  public void basicConfigBigInteger() {
    String basicConfig = "{\"bigInteger\":12345678912345678913,\"bigDecimal\":123.123872538}";

    Config config = configProvider.load(basicConfig);

    assertThat(config.getBigInteger("bigInteger").orElse(null))
        .isEqualTo("12345678912345678913");

    assertThat(config.getBigDecimal("bigDecimal").orElse(null))
        .isEqualTo("123.123872538");
  }

  @Test
  public void basicConfigInvalidTypes() {
    String basicConfig = "{\"number\":\"hi\",\"string\":123,\"list\":\"another string\"}\n";

    Config config = configProvider.load(basicConfig);

    assertThat(config.getNumber("number"))
        .isEmpty();

    assertThat(config.getString("string"))
        .isEmpty();

    assertThat(config.getIntegerList("list"))
        .isEmpty();
  }
}
