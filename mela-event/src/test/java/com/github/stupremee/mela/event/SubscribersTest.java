package com.github.stupremee.mela.event;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.stupremee.mela.event.subscriber.Subscriber;
import com.github.stupremee.mela.event.subscriber.SubscriberFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.function.Consumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 01.08.19
 */
final class SubscribersTest {

  private SubscriberFactory subscriberFactory;
  private Subscriber subscriber;
  private Object event;

  @BeforeEach
  void setUp() {
    Injector injector = Guice.createInjector(EventModule.create());
    this.subscriberFactory = injector.getInstance(SubscriberFactory.class);
  }

  @AfterEach
  void tearDown() {
    this.subscriber = null;
  }

  @Test
  void testCallbackSubscriberWithoutType() {
    setEventTo(new Object());

    CheckCalledConsumer<Object> callback = createCheckCalledConsumer();
    subscriber = subscriberFactory.fromCallback(callback);

    callEvent();

    assertThat(callback.called).isTrue();
  }

  @Test
  void testCallbackSubscriberWithType() {
    setEventTo("Hello!");

    CheckCalledConsumer<String> callback = createCheckCalledConsumer();
    subscriber = subscriberFactory.fromCallback(String.class, callback);

    callEvent();

    assertThat(subscriber.supportsType(String.class)).isTrue();
    assertThat(subscriber.supportsType(CharSequence.class)).isTrue();

    assertThat(callback.called).isTrue();
  }

  private <T> CheckCalledConsumer<T> createCheckCalledConsumer() {
    return new CheckCalledConsumer<>(validateEventCallback());
  }

  private <T> Consumer<T> validateEventCallback() {
    return obj -> assertThat(obj).isEqualTo(event);
  }

  private void callEvent() {
    this.subscriber.call(event);
  }

  private void setEventTo(Object event) {
    this.event = event;
  }

  private static final class CheckCalledConsumer<T> implements Consumer<T> {

    private final Consumer<T> action;
    private boolean called = false;

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
