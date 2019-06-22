package com.github.stupremee.mela.config.yaml;

import com.google.common.base.Charsets;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Stu <https://github.com/Stupremee>
 * @since 22.06.19
 */
final class YamlContentConfigProvider extends YamlConfigProvider {

  private final String content;

  YamlContentConfigProvider(String content) {
    this.content = content;
  }

  @Override
  public InputStream getSource() {
    return new ByteArrayInputStream(content.getBytes(Charsets.UTF_8));
  }
}
