package com.github.stupremee.mela.serialization.deserializer;

import com.github.stupremee.mela.beans.Bean;
import com.github.stupremee.mela.serialization.serializer.BeanSerializer;
import java.io.Reader;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
public interface BeanDeserializer {

  /**
   * Returns the default BeanDeserializer that will serialize beans to a json string.
   *
   * @return The {@link BeanSerializer} that will serialize beans to json
   */
  static BeanDeserializer json() {
    return JsonBeanDeserializer.getInstance();
  }

  /**
   * Tries to deserialize the given string to a bean and closes the reader after reading.
   *
   * @param beanClass The class of the bean
   * @param reader The reader from which the string representing the bean is to be read
   * @param <T> The type of the bean
   * @return The deserialized bean
   */
  @NotNull <T extends Bean> Optional<T> deserialize(@NotNull Class<T> beanClass,
      @NotNull Reader reader);

  /**
   * Tries to deserialize the given string to a bean.
   *
   * @param beanClass The class of the bean
   * @param value The string representing the bean
   * @param <T> The type of the bean
   * @return The deserialized bean
   */
  @NotNull <T extends Bean> Optional<T> deserialize(@NotNull Class<T> beanClass,
      @NotNull String value);

}
