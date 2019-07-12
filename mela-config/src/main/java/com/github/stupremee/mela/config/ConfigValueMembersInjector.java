package com.github.stupremee.mela.config;

import com.google.inject.MembersInjector;
import java.lang.reflect.Field;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 12.07.19
 */
final class ConfigValueMembersInjector<T> implements MembersInjector<T> {

  private final Field field;
  private final Config config;
  private final String path;

  private ConfigValueMembersInjector(Field field, Config config, String path) {
    this.field = field;
    this.config = config;
    this.path = path;
  }

  @Override
  public void injectMembers(T instance) {
    try {
      if (field.trySetAccessible()) {
        Object value = config.getAs(path, field.getType()).orElse(null);
        field.set(instance, value);
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  static <T> MembersInjector<T> create(Field field, Config config, String path) {
    return new ConfigValueMembersInjector<>(field, config, path);
  }
}
