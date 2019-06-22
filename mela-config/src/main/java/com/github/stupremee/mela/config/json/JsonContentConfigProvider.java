package com.github.stupremee.mela.config.json;

import com.google.common.base.Charsets;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 22.06.19
 */
final class JsonContentConfigProvider extends JsonConfigProvider {

  private final String content;

  JsonContentConfigProvider(String content) {
    this.content = content;
  }

  @Override
  public InputStream getSource() {
    return new ByteArrayInputStream(content.getBytes(Charsets.UTF_8));
  }
}
