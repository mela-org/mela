package com.github.stupremee.mela.config.annotations;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 20.06.19
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
public @interface ConfigProperty {

  /**
   * The path of the value.
   */
  String value();

}
