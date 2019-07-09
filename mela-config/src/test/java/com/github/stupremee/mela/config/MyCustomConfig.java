package com.github.stupremee.mela.config;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 09.07.19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@BindingAnnotation
@interface MyCustomConfig {

}
