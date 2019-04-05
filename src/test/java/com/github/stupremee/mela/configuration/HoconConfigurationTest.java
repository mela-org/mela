package com.github.stupremee.mela.configuration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 04.04.2019
 */
public class HoconConfigurationTest {

  private ConfigurationFactory factory;

  @Before
  public void setup() {
    this.factory = HoconConfigurationFactory.create();
  }

  // Just one test because I am too lazy
  @Test
  public void hoconTest() throws IOException {
    String hocon = "conf {\n"
        + "  name = \"default\"\n"
        + "  title = \"Simple Title\"\n"
        + "  nested {\n"
        + "    whitelistIds = [1, 22, 34]\n"
        + "  }\n"
        + "  \n"
        + "  combined = ${conf.name} ${conf.title} \n"
        + "}\n"
        + "\n"
        + "featureFlags {\n"
        + "  featureA = \"yes\"\n"
        + "  featureB = true\n"
        + "}";
    var cfg = factory.load(hocon);
    assertEquals("default", cfg.getString("conf.name").getOrNull());
    assertEquals("Simple Title", cfg.getString("conf.title").getOrNull());
    assertEquals(List.of(1, 22, 34), cfg.getNumbers("conf.nested.whitelistIds"));
    assertEquals("default Simple Title", cfg.getString("conf.combined").getOrNull());
    assertEquals("yes", cfg.getString("featureFlags.featureA").getOrNull());
    assertEquals(true, cfg.getBool("featureFlags.featureB").getOrNull());

    String writeResult = "conf {\n"
        + "    combined=\"default Simple Title\"\n"
        + "    name=default\n"
        + "    nested {\n"
        + "        whitelistIds=[\n"
        + "            1,\n"
        + "            22,\n"
        + "            34\n"
        + "        ]\n"
        + "    }\n"
        + "    title=\"Simple Title\"\n"
        + "}\n"
        + "featureFlags {\n"
        + "    featureA=yes\n"
        + "    featureB=true\n"
        + "}";
    assertEquals(writeResult, cfg.writeToString(HoconConfigurationParser.getInstance()).trim());
  }

}
