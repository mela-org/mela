package com.github.stupremee.mela.config.json;

import com.google.inject.ProvisionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 22.06.19
 */
final class JsonFileConfigProvider extends JsonConfigProvider {

  private final File file;

  JsonFileConfigProvider(File file) {
    this.file = file;
  }

  @Override
  public InputStream getSource() {
    try {
      return new FileInputStream(file);
    } catch (FileNotFoundException cause) {
      throw new ProvisionException("Failed to load json config file.", cause);
    }
  }
}
