package com.github.stupremee.mela.config;

import com.fasterxml.jackson.core.JsonFactory;
import java.io.InputStream;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 22.06.19
 */
public interface ConfigProvider {

  /**
   * Returns the source of the config.
   */
  InputStream getSource();

  /**
   * Returns the {@link JsonFactory} that will be used to parse the source of this provider.
   */
  JsonFactory getJsonFactory();

}