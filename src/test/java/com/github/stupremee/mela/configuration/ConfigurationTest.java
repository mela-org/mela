package com.github.stupremee.mela.configuration;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class ConfigurationTest {

  private ConfigurationFactory factory;

  @Before
  public void setUp() {
    factory = TomlConfigurationFactory.create();
  }

  @Test
  public void string() throws IOException {
    String toml = "title = \"Awesome Names\"\n"
        + "[names]\n"
        + "god = \"Stu\"";
    Configuration cfg = factory.load(toml);
    cfg.set("set", "yes");
    cfg.set("settings.true", "no");
    assertEquals("Awesome Names", cfg.string("title").getOrElse("no"));
    assertEquals("Stu", cfg.string("names.god").getOrElse("no"));
    assertEquals("yes", cfg.string("set").getOrElse("no"));
    assertEquals("no", cfg.string("settings.true").getOrElse("no!"));
  }

  @Test
  public void strings() throws IOException {
    String toml = "strings = [\"Stu\", \"Pascal\", \"Vale\"]\n"
        + "more = [\n"
        + "\"Jes\",\n"
        + "\"No\"\n"
        + "]\n";
    Configuration cfg = factory.load(toml);
    cfg.set("set", new String[]{"yes", "no"});
    assertArrayEquals(cfg.strings("strings").toArray(String[]::new),
        new String[]{"Stu", "Pascal", "Vale"});
    assertArrayEquals(cfg.strings("more").toArray(String[]::new), new String[]{"Jes", "No"});
    assertArrayEquals(cfg.strings("set").toArray(String[]::new), new String[]{"yes", "no"});
  }

  @Test
  public void number() throws IOException {
    String toml = "title = 1\n"
        + "[names]\n"
        + "god = 3";
    Configuration cfg = factory.load(toml);
    cfg.set("set", 4);
    cfg.set("settings.true", 2);
    assertEquals(1, cfg.number("title").getOrNull().intValue());
    assertEquals(3, cfg.number("names.god").getOrNull().intValue());
    assertEquals(4, cfg.number("set").getOrNull().intValue());
    assertEquals(2, cfg.number("settings.true").getOrNull().intValue());
  }

  @Test
  public void numbers() throws IOException {
    String toml = "strings = [1, 2, 3]\n"
        + "more = [\n"
        + "4,\n"
        + "5\n"
        + "]\n";
    Configuration cfg = factory.load(toml);
    cfg.set("set", new Long[]{67L, 68L});
    assertArrayEquals(cfg.numbers("strings").toArray(Long[]::new), new Long[]{1L, 2L, 3L});
    assertArrayEquals(cfg.numbers("more").toArray(Long[]::new), new Long[]{4L, 5L});
    assertArrayEquals(cfg.numbers("set").toArray(Long[]::new), new Long[]{67L, 68L});
  }

  @Test
  public void object() throws IOException {
    String toml = "title = 1\n"
        + "[names]\n"
        + "god = 3";
    Configuration cfg = factory.load(toml);
    cfg.set("set", 4L);
    cfg.set("settings.true", 2L);
    assertEquals(1L, cfg.object("title").getOrNull());
    assertEquals(3L, cfg.object("names.god").getOrNull());
    assertEquals(4L, cfg.object("set").getOrNull());
    assertEquals(2L, cfg.object("settings.true").getOrNull());
  }

  @Test
  public void objects() throws IOException {
    String toml = "strings = [1, 2, 3]\n"
        + "more = [\n"
        + "4,\n"
        + "5\n"
        + "]\n";
    Configuration cfg = factory.load(toml);
    cfg.set("set", new Object[]{67L, 68L});
    assertArrayEquals(cfg.objects("strings").toArray(Object[]::new), new Object[]{1L, 2L, 3L});
    assertArrayEquals(cfg.objects("more").toArray(Object[]::new), new Object[]{4L, 5L});
    assertArrayEquals(cfg.objects("set").toArray(Object[]::new), new Object[]{67L, 68L});
  }

  @Test
  public void bool() throws IOException {
    String toml = "title = true\n"
        + "[names]\n"
        + "god = false";
    Configuration cfg = factory.load(toml);
    cfg.set("set", false);
    cfg.set("settings.true", true);
    assertTrue(cfg.bool("title").getOrNull());
    assertFalse(cfg.bool("names.god").getOrNull());
    assertFalse(cfg.bool("set").getOrNull());
    assertTrue(cfg.bool("settings.true").getOrNull());
  }

  @Test
  public void bools() throws IOException {
    String toml = "strings = [true, false, true]\n"
        + "more = [\n"
        + "true,\n"
        + "false\n"
        + "]\n";
    Configuration cfg = factory.load(toml);
    cfg.set("set", new Boolean[]{false, false});
    assertArrayEquals(cfg.objects("strings").toArray(Boolean[]::new),
        new Boolean[]{true, false, true});
    assertArrayEquals(cfg.objects("more").toArray(Boolean[]::new), new Boolean[]{true, false});
    assertArrayEquals(cfg.objects("set").toArray(Boolean[]::new), new Boolean[]{false, false});
  }
}