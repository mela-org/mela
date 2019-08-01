package com.github.stupremee.mela.event.subscriber;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.stupremee.mela.event.EventModule;
import com.github.stupremee.mela.event.listener.GenericListener;
import com.github.stupremee.mela.event.listener.Listener;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.function.Consumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 01.08.19
 */
abstract class SubscriberTestBase {

  SubscriberFactory subscriberFactory;
  Subscriber subscriber;
  Object event;

  @BeforeEach
  void setUp() {
    Injector injector = Guice.createInjector(EventModule.create());
    this.subscriberFactory = injector.getInstance(SubscriberFactory.class);
  }

  @AfterEach
  void tearDown() {
    this.subscriber = null;
  }

  <T> CheckCalledConsumer<T> createCheckCalledConsumer() {
    return new CheckCalledConsumer<>(validateEventCallback());
  }

  <T> CheckCalledGenericListener<T> createCheckCalledGenericListener(Class<T> type) {
    return new CheckCalledGenericListener<>(type, validateEventCallback());
  }

  CheckCalledListener createCheckCalledListener() {
    return new CheckCalledListener(validateEventCallback());
  }

  void callEvent() {
    this.subscriber.call(event);
  }

  void setEventTo(Object event) {
    this.event = event;
  }

  void setSubscriberTo(Subscriber subscriber) {
    this.subscriber = subscriber;
  }

  private <T> Consumer<T> validateEventCallback() {
    return obj -> assertThat(obj).isEqualTo(event);
  }

  static final class CheckCalledListener implements Listener {

    final Consumer<Object> action;
    boolean called = false;

    private CheckCalledListener(Consumer<Object> action) {
      this.action = action;
    }

    @Override
    public void onEvent(Object event) {
      this.called = true;
      action.accept(event);
    }
  }

  static final class CheckCalledGenericListener<T> implements GenericListener<T> {

    final Class<T> type;
    final Consumer<T> action;
    boolean called = false;

    CheckCalledGenericListener(Class<T> type, Consumer<T> action) {
      this.type = type;
      this.action = action;
    }

    @Override
    public void onEvent(T event) {
      called = true;
      action.accept(event);
    }

    @Override
    public Class<T> getEventType() {
      return type;
    }
  }

  static final class CheckCalledConsumer<T> implements Consumer<T> {

    final Consumer<T> action;
    boolean called = false;

    private CheckCalledConsumer(Consumer<T> action) {
      this.action = action;
    }

    @Override
    public void accept(T t) {
      this.called = true;
      action.accept(t);
    }
  }

}
