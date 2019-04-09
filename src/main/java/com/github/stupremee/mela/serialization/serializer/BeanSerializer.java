package com.github.stupremee.mela.serialization.serializer;

import com.github.stupremee.mela.beans.SnowflakeBean;
import java.io.Writer;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
public interface BeanSerializer {

  /**
   * Returns the default BeanSerializer that will serialize beans to a json string.
   *
   * @return The {@link BeanSerializer} that will serialize beans to json
   */
  static BeanSerializer json() {
    return JsonBeanSerializer.getInstance();
  }

  /**
   * Serializes the bean and then writes the result into the writer.
   *
   * @param writer The writer to write the string to
   * @param bean The bean to serialize
   */
  void serialize(@Nonnull Writer writer, @Nonnull SnowflakeBean bean);

  /**
   * Serializes a {@link Object} to a string.
   *
   * @param bean The bean to parse
   * @return A {@link String} representing the bean or {@code {}} if a error occurs while
   *     serializing
   */
  @Nonnull
  String serialize(@Nonnull SnowflakeBean bean);
}
