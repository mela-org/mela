package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.interceptor.Interceptor;

import java.lang.annotation.Annotation;

public interface InterceptorBindingBuilder<T extends Annotation> {

  CommandNode with(Interceptor<T> interceptor);

}