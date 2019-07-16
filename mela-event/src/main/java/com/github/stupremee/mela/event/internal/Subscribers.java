package com.github.stupremee.mela.event.internal;

import com.github.stupremee.mela.event.listener.GenericListener;
import com.github.stupremee.mela.event.listener.Listener;
import com.github.stupremee.mela.event.subscriber.Subscriber;
import com.google.common.base.Objects;
import java.util.function.Consumer;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 14.07.19
 */
final class Subscribers {

  private Subscribers() {

  }

  static <T> Subscriber fromGenericListener(GenericListener<T> listener) {
    return new GenericListenerSubscriber<>(listener);
  }

  static Subscriber fromListener(Listener listener) {
    return new ListenerSubscriber(listener);
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
    public boolean supportsType(Class<?> type) {
      return this.type.isAssignableFrom(type);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(callback, type);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return false;
      }

      if (!(obj instanceof CallbackSubscriber)) {
        return false;
      }

      CallbackSubscriber<?> that = (CallbackSubscriber<?>) obj;
      return Objects.equal(that.type, type)
          && Objects.equal(that.callback, callback);
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
    public boolean supportsType(Class<?> type) {
      return true;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(listener);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return false;
      }

      if (!(obj instanceof ListenerSubscriber)) {
        return false;
      }

      ListenerSubscriber that = (ListenerSubscriber) obj;
      return Objects.equal(that.listener, listener);
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
    public boolean supportsType(Class<?> type) {
      return listener.getEventType().isAssignableFrom(type);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(listener);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return false;
      }

      if (!(obj instanceof GenericListenerSubscriber)) {
        return false;
      }

      GenericListenerSubscriber<?> that = (GenericListenerSubscriber<?>) obj;
      return Objects.equal(that.listener, listener);
    }
  }
}
