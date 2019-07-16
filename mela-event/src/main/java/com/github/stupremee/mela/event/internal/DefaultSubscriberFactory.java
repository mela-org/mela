package com.github.stupremee.mela.event.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.stupremee.mela.event.annotations.Subscribe;
import com.github.stupremee.mela.event.internal.EventMethodsSubscriber.EventMethod;
import com.github.stupremee.mela.event.listener.GenericListener;
import com.github.stupremee.mela.event.listener.Listener;
import com.github.stupremee.mela.event.subscriber.Subscriber;
import com.github.stupremee.mela.event.subscriber.SubscriberFactory;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 16.07.19
 */
public final class DefaultSubscriberFactory implements SubscriberFactory {

  private final Injector injector;

  @Inject
  DefaultSubscriberFactory(Injector injector) {
    this.injector = injector;
  }

  @Override
  public Subscriber fromListener(Object listener) {
    checkNotNull(listener, "listener can't be null.");
    return new EventMethodsSubscriber(collectEventMethods(listener));
  }

  @Override
  public Subscriber fromListener(Listener listener) {
    checkNotNull(listener, "listener can't be null.");
    return Subscribers.fromListener(listener);
  }

  @Override
  public <T> Subscriber fromListener(GenericListener<T> listener) {
    checkNotNull(listener, "listener can't be null.");
    return Subscribers.fromGenericListener(listener);
  }

  @Override
  public <T> Subscriber fromCallback(Class<T> eventType, Consumer<T> callback) {
    checkNotNull(eventType, "eventType can't be null.");
    checkNotNull(callback, "callback can't be null.");
    return Subscribers.fromCallback(callback, eventType);
  }

  private List<EventMethod> collectEventMethods(Object listener) {
    return Arrays.stream(listener.getClass().getDeclaredMethods())
        .filter(method -> !Modifier.isStatic(method.getModifiers()))
        .filter(method -> method.isAnnotationPresent(Subscribe.class))
        .filter(method -> method.getParameterTypes().length == 1)
        .map(method -> {
          Class<?> type = method.getParameterTypes()[0];
          return new EventMethod(method, type, listener);
        })
        .collect(Collectors.toUnmodifiableList());
  }
}
