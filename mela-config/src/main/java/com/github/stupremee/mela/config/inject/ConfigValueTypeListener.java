package com.github.stupremee.mela.config.inject;

import com.github.stupremee.mela.config.Config;
import com.github.stupremee.mela.config.annotations.ConfigValue;
import com.google.inject.BindingAnnotation;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 12.07.19
 */
public final class ConfigValueTypeListener implements TypeListener {

  private ConfigValueTypeListener() {
  }

  @Override
  public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
    Class<?> clazz = type.getRawType();
    do {
      Arrays.stream(clazz.getDeclaredFields())
          .filter(field -> field.isAnnotationPresent(ConfigValue.class))
          .map(field -> createMemberInjectorForField(field, encounter))
          .forEach(encounter::register);
    } while ((clazz = clazz.getSuperclass()) != null);
  }

  private <I> MembersInjector<Object> createMemberInjectorForField(Field field,
      TypeEncounter<I> encounter) {
    String path = field.getAnnotation(ConfigValue.class).value();
    Config config = Arrays.stream(field.getDeclaredAnnotations())
        .filter(annotation -> !annotation.annotationType().equals(ConfigValue.class))
        .filter(this::isBindingAnnotation)
        .findFirst()
        .map(annotation -> encounter.getProvider(Key.get(Config.class, annotation)))
        .or(() -> Optional.of(encounter.getProvider(Config.class)))
        .map(Provider::get)
        .orElseThrow();
    return ConfigValueMembersInjector.create(field, config, path);
  }

  private boolean isBindingAnnotation(Annotation annotation) {
    return annotation.annotationType().isAnnotationPresent(BindingAnnotation.class);
  }

  public static TypeListener create() {
    return new ConfigValueTypeListener();
  }
}
