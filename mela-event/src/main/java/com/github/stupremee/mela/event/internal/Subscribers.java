package com.github.stupremee.mela.event.internal;

import com.github.stupremee.mela.event.listener.GenericListener;
import com.github.stupremee.mela.event.listener.Listener;
import com.github.stupremee.mela.event.subscriber.Subscriber;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 14.07.19
 */
final class Subscribers {

  private Subscribers() {

  }

  static <T> Subscriber fromCallback(Consumer<T> callback, Class<T> eventType) {
    return new CallbackSubscriber<>(callback, eventType);
  }

  private static final class CallbackSubscriber<T> implements Subscriber {

    private final Class<T> type;
    private final Consumer<T> callback;

    private CallbackSubscriber(Consumer<T> callback, Class<T> type) {
      this.callback = callback;
      this.type = type;
    }

    @Override
    public void call(Object event) {
      if (type.isAssignableFrom(event.getClass())) {
        callback.accept(type.cast(event));
      }
    }

    @Override
    public List<Class<?>> getSupportedTypes() {
      return Collections.singletonList(type);
    }
  }

  private static final class ListenerSubscriber implements Subscriber {

    private final Listener listener;

    private ListenerSubscriber(Listener listener) {
      this.listener = listener;
    }

    @Override
    public void call(Object event) {
      listener.onEvent(event);
    }

    @Override
    public List<Class<?>> getSupportedTypes() {
      return Collections.singletonList(Object.class);
    }
  }

  private static final class GenericListenerSubscriber<T> implements Subscriber {

    private final GenericListener<T> listener;

    private GenericListenerSubscriber(GenericListener<T> listener) {
      this.listener = listener;
    }

    @Override
    public void call(Object event) {
      if (listener.getEventType().isAssignableFrom(event.getClass())) {
        listener.onEvent(listener.getEventType().cast(event));
      }
    }

    @Override
    public List<Class<?>> getSupportedTypes() {
      return Collections.singletonList(listener.getEventType());
    }
  }
}
