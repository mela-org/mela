package com.github.stupremee.mela.event.subscriber;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 01.08.19
 */
final class CallbackSubscriberTest extends SubscriberTestBase {

  @Test
  void testCallbackSubscriberWithoutType() {
    setEventTo(new Object());

    CheckCalledConsumer<Object> callback = createCheckCalledConsumer();
    setSubscriberTo(subscriberFactory.fromCallback(callback));

    callEvent();

    assertThat(callback.called).isTrue();
  }

  @Test
  void testCallbackSubscriberWithType() {
    CheckCalledConsumer<String> callback = createCheckCalledConsumer();
    setSubscriberTo(subscriberFactory.fromCallback(String.class, callback));

    setEventTo("Hello!");
    callEvent();
    assertThat(callback.called).isTrue();

    callback.called = false;
    setEventTo(123);
    callEvent();
    assertThat(callback.called).isFalse();
  }

}