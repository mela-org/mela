package com.github.stupremee.mela.configuration;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;

public class YamlConfigurationTest {

  private ConfigurationFactory factory;

  @Before
  public void setUp() {
    factory = ConfigurationFactory.yaml();
  }

  @Test
  public void string() throws IOException {
    String yaml = "title: names\nmartin:\n"
        + "    name: Martin D'vloper\n"
        + "    job: Developer\n"
        + "    skill: Elite";
    Configuration cfg = factory.load(yaml);
    cfg.set("description", "Employee Record");
    cfg.set("martin.nickname", "BigM");
    assertEquals("names", cfg.getString("title").getOrElse("no"));
    assertEquals("Martin D'vloper", cfg.getString("martin.name").getOrElse("no"));
    assertEquals("Developer", cfg.getString("martin.job").getOrElse("no"));
    assertEquals("Elite", cfg.getString("martin.skill").getOrElse("no"));
    assertEquals("Employee Record", cfg.getString("description").getOrElse("no"));
    assertEquals("BigM", cfg.getString("martin.nickname").getOrElse("no"));
  }

  @Test
  public void strings() throws IOException {
    String yaml = "# A list of tasty fruits\n"
        + "fruits:\n"
        + "    - Apple\n"
        + "    - Orange\n"
        + "    - Strawberry\n"
        + "    - Mango";
    Configuration cfg = factory.load(yaml);
    cfg.set("drinks", List.of("Cola", "Fanta"));
    assertEquals(cfg.getStrings("fruits"), List.of("Apple", "Orange", "Strawberry", "Mango"));
    assertEquals(cfg.getStrings("drinks"), List.of("Cola", "Fanta"));
  }

  @Test
  public void number() throws IOException {
    String yaml = "foo: 1\n"
        + "bar:\n"
        + " names: 26";
    Configuration cfg = factory.load(yaml);
    cfg.set("set", 4);
    cfg.set("settings.true", 2);
    assertEquals(1, cfg.getNumber("foo").getOrNull().intValue());
    assertEquals(26, cfg.getNumber("bar.names").getOrNull().intValue());
    assertEquals(4, cfg.getNumber("set").getOrNull().intValue());
    assertEquals(2, cfg.getNumber("settings.true").getOrNull().intValue());
  }

  @Test
  public void numbers() throws IOException {
    String yaml = "# A list of tasty fruits\n"
        + "fruits:\n"
        + "    - 1\n"
        + "    - 2\n"
        + "    - 3\n"
        + "    - 47";
    Configuration cfg = factory.load(yaml);
    cfg.set("set", List.of(45L, 12L));
    assertArrayEquals(cfg.getNumbers("fruits").toArray(Number[]::new), new Number[]{1, 2, 3, 47});
    assertArrayEquals(cfg.getNumbers("set").toArray(Number[]::new), new Number[]{45L, 12L});
  }

  @Test
  public void object() throws IOException {
    String yaml = "name: Martin D'vloper\n"
        + "job: Developer\n"
        + "skill: Elite\n"
        + "employed: True\n"
        + "foods:\n"
        + "    - Apple\n"
        + "    - Orange\n"
        + "    - Strawberry\n"
        + "    - Mango\n"
        + "languages:\n"
        + "    perl: Elite\n"
        + "    python: Elite\n"
        + "    pascal: Lame";
    Configuration cfg = factory.load(yaml);
    cfg.set("set", 4L);
    cfg.set("settings.true", 2L);
    assertFalse(cfg.getObject("name").filter(o -> o instanceof String).isEmpty());
    assertFalse(cfg.getObject("job").filter(o -> o instanceof String).isEmpty());
    assertFalse(cfg.getObject("skill").filter(o -> o instanceof String).isEmpty());
    assertFalse(cfg.getObject("employed").filter(o -> o instanceof Boolean).isEmpty());
    assertFalse(cfg.getObject("foods").filter(o -> o instanceof List).isEmpty());
    assertFalse(cfg.getObject("languages").filter(Objects::nonNull).isEmpty());
    assertFalse(cfg.getObject("set").filter(o -> o instanceof Long).isEmpty());
    assertFalse(cfg.getObject("settings.true").filter(o -> o instanceof Long).isEmpty());
  }

  @Test
  public void objects() throws IOException {
    String yaml = "foods:\n"
        + "    - Apple\n"
        + "    - Orange\n"
        + "    - Strawberry\n"
        + "    - Mango";
    Configuration cfg = factory.load(yaml);
    cfg.set("set", List.<Object>of(1, 2, 3));
    assertEquals(cfg.getObjects("foods"), List.of("Apple", "Orange", "Strawberry", "Mango"));
    assertEquals(cfg.getObjects("set"), List.of(1, 2, 3));
  }

  @Test
  public void bool() throws IOException {
    String yaml = "create_key: yes\n"
        + "needs_agent: no\n"
        + "knows_oop: True\n"
        + "likes_emacs: TRUE\n"
        + "uses_cvs: false";
    Configuration cfg = factory.load(yaml);
    cfg.set("set", false);
    cfg.set("settings.true", true);
    assertTrue(cfg.getBool("create_key").getOrNull());
    assertFalse(cfg.getBool("needs_agent").getOrNull());
    assertFalse(cfg.getBool("uses_cvs").getOrNull());
    assertTrue(cfg.getBool("knows_oop").getOrNull());
    assertFalse(cfg.getBool("set").getOrNull());
    assertTrue(cfg.getBool("settings.true").getOrNull());
  }

  @Test
  public void bools() throws IOException {
    String yaml = "bools: [true, false, true]\n"
        + "more:\n"
        + " - true\n"
        + " - false";
    Configuration cfg = factory.load(yaml);
    cfg.set("set", List.of(false, true, false));
    assertEquals(cfg.getObjects("bools"), List.of(true, false, true));
    assertEquals(cfg.getObjects("more"), List.of(true, false));
    assertEquals(cfg.getObjects("set"), List.of(false, true, false));
  }

  @Test
  public void writeTest() throws IOException {
    String yaml = "# An employee record\n"
        + "name: Martin D'vloper\n"
        + "job: Developer\n"
        + "skill: Elite\n"
        + "employed: True\n"
        + "foods:\n"
        + "    - Apple\n"
        + "    - Orange\n"
        + "    - Strawberry\n"
        + "    - Mango\n"
        + "languages:\n"
        + "    perl: Elite\n"
        + "    python: Elite\n"
        + "    pascal: Lame\n"
        + "education: |\n"
        + "    4 GCSEs\n"
        + "    3 A-Levels\n"
        + "    BSc in the Internet of Things";
    Configuration cfg = factory.load(yaml);
    assertEquals("---\n"
            + "name: \"Martin D'vloper\"\n"
            + "job: \"Developer\"\n"
            + "skill: \"Elite\"\n"
            + "employed: true\n"
            + "foods:\n"
            + "- \"Apple\"\n"
            + "- \"Orange\"\n"
            + "- \"Strawberry\"\n"
            + "- \"Mango\"\n"
            + "languages:\n"
            + "  perl: \"Elite\"\n"
            + "  python: \"Elite\"\n"
            + "  pascal: \"Lame\"\n"
            + "education: \"4 GCSEs\\n3 A-Levels\\nBSc in the Internet of Things\"".trim(),
        cfg.writeToString(YamlConfigurationParser.getInstance()).trim());
  }
}