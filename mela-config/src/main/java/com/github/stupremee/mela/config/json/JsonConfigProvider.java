package com.github.stupremee.mela.config.json;

import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.github.stupremee.mela.config.ConfigProvider;
import java.io.File;
import java.io.InputStream;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 22.06.19
 */
public abstract class JsonConfigProvider implements ConfigProvider {

  @Override
  public final JsonFactory getJsonFactory() {
    return new MappingJsonFactory();
  }

  /**
   * Creates a {@link ConfigProvider} that will parse the given {@link InputStream} as json.
   */
  public static ConfigProvider create(InputStream source) {
    checkNotNull(source, "source can't be null.");
    return new JsonConfigProvider() {
      @Override
      public InputStream getSource() {
        return source;
      }
    };
  }

  /**
   * Creates a {@link ConfigProvider} that will parse the content of the given {@link File} as
   * json.
   */
  public static ConfigProvider of(File file) {
    checkNotNull(file, "file can't be null.");
    return new JsonFileConfigProvider(file);
  }

  /**
   * Creates a new {@link ConfigProvider} that will parse the given content as json.
   */
  public static ConfigProvider of(String content) {
    checkNotNull(content, "content can't be null.");
    return new JsonContentConfigProvider(content);
  }

}