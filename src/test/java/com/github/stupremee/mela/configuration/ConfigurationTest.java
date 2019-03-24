package com.github.stupremee.mela.configuration;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
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
    assertEquals("Awesome Names", cfg.string("title").getOrElse("no"));
    assertEquals("Stu", cfg.string("names.god").getOrElse("no"));
  }

  @Test
  public void strings() throws IOException {
    String toml = "strings = [\"Stu\", \"Pascal\", \"Vale\"]\n"
        + "more = [\n"
        + "\"Jes\",\n"
        + "\"No\"\n"
        + "]\n";
    Configuration cfg = factory.load(toml);
    assertArrayEquals(cfg.strings("strings").toArray(String[]::new), new String[]{"Stu", "Pascal", "Vale"});
    assertArrayEquals(cfg.strings("more").toArray(String[]::new), new String[]{"Jes", "No"});
  }
}