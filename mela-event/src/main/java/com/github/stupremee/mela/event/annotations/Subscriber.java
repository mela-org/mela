package com.github.stupremee.mela.event.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a class as a Subscriber which can receive events.
 *
 * @author Stu (https://github.com/Stupremee)
 * @since 10.07.19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Subscriber {

}
