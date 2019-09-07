package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.CommandInterceptor;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class CommandMultibinder {

  private static final TypeLiteral<ArgumentMapper<?>> MAPPER_LITERAL = new TypeLiteral<>() {};
  private static final TypeLiteral<CommandInterceptor<?>> INTERCEPTOR_LITERAL = new TypeLiteral<>() {};
  private static final TypeLiteral<ExceptionHandler<?>> HANDLER_LITERAL = new TypeLiteral<>() {};

  private final Binder binder;

  private Multibinder<ArgumentMapper<?>> mapperBinder;
  private Multibinder<Object> commandObjectBinder;
  private Multibinder<CommandInterceptor<?>> interceptorBinder;
  private Multibinder<ExceptionHandler<?>> handlerBinder;

  CommandMultibinder(Binder binder) {
    this.binder = binder;
    commandObjectBinder = Multibinder.newSetBinder(binder, Object.class, Commands.class);
    mapperBinder = Multibinder.newSetBinder(binder, MAPPER_LITERAL, Mappers.class);
    interceptorBinder = Multibinder.newSetBinder(binder, INTERCEPTOR_LITERAL, Interceptors.class);
    handlerBinder = Multibinder.newSetBinder(binder, HANDLER_LITERAL, Handlers.class);
  }

  Multibinder<Object> commandObjectBinder() {
    return commandObjectBinder;
  }

  Multibinder<ArgumentMapper<?>> mapperBinder() {
    return mapperBinder;
  }

  Multibinder<CommandInterceptor<?>> interceptorBinder() {
    return interceptorBinder;
  }

  Multibinder<ExceptionHandler<?>> handlerBinder() {
    return handlerBinder;
  }

  Binder binder() {
    return binder;
  }

}