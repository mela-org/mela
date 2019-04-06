package com.github.stupremee.mela.configuration;

import com.google.common.base.Preconditions;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 04.04.2019
 */
public class HoconConfigurationParser implements ConfigurationParser {

  private final ConfigRenderOptions renderOptions;

  private static class Lazy {

    private static HoconConfigurationParser INSTANCE = new HoconConfigurationParser();
  }

  private HoconConfigurationParser() {
    this.renderOptions = ConfigRenderOptions.defaults()
        .setJson(false)
        .setOriginComments(false)
        .setComments(true);
  }

  @Override
  public void serialize(@NotNull Map<String, Object> config, @NotNull Writer writer)
      throws IOException {
    Preconditions.checkNotNull(config, "config can't be null.");
    Preconditions.checkNotNull(writer, "writer can't be null.");

    String hocon = ConfigFactory.parseMap(config).root()
        .render(renderOptions);
    writer.write(hocon);
  }

  static ConfigurationParser getInstance() {
    return Lazy.INSTANCE;
  }
}
