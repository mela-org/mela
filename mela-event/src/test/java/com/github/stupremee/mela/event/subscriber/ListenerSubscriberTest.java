package com.github.stupremee.mela.event.subscriber;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 01.08.19
 */
final class ListenerSubscriberTest extends SubscriberTestBase {

  @Test
  void testListenerSubscriber() {

    CheckCalledListener listener = createCheckCalledListener();
    setSubscriberTo(subscriberFactory.fromListener(listener));

    setEventTo("some event is here");
    callEvent();
    assertThat(listener.called).isTrue();
  }

  @Test
  void testGenericListenerSubscriber() {

    CheckCalledGenericListener<Integer> listener = createCheckCalledGenericListener(Integer.class);
    setSubscriberTo(subscriberFactory.fromListener(listener));

    setEventTo("some event is here");
    callEvent();
    assertThat(listener.called).isFalse();

    listener.called = false;
    setEventTo(123);
    callEvent();
    assertThat(listener.called).isTrue();
  }

}
