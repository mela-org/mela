package com.github.stupremee.mela.config.yaml;

import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.stupremee.mela.config.ConfigProvider;
import java.io.File;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 22.06.19
 */
public abstract class YamlConfigProvider implements ConfigProvider {

  @Override
  public final JsonFactory getJsonFactory() {
    return new YAMLFactory();
  }

  /**
   * Creates a {@link ConfigProvider} that will parse the content of the given {@link File} as
   * yaml.
   */
  public static ConfigProvider ofFile(File file) {
    checkNotNull(file, "file can't be null.");
    return new YamlFileConfigProvider(file);
  }

  /**
   * Creates a new {@link ConfigProvider} that will parse the given content as yaml.
   */
  public static ConfigProvider ofContent(String content) {
    checkNotNull(content, "content can't be null.");
    return new YamlContentConfigProvider(content);
  }
}
