package com.github.stupremee.mela.config.annotations;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that is used to bind a {@link com.fasterxml.jackson.databind.ObjectMapper} to the
 * mapper that will be used to parse configs.
 *
 * @author Stu
 * @since 22.06.19
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface ConfigMapper {

}
