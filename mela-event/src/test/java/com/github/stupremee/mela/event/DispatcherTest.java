package com.github.stupremee.mela.event;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.stupremee.mela.event.dispatchers.Dispatchers;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 10.07.19
 */
final class DispatcherTest {

  private final List<Subscriber> dispatchedSubscribers = new ArrayList<>();

  private final Subscriber firstStringSubscriber = createStringSubscriber("firstString");
  private final Subscriber secondStringSubscriber = createStringSubscriber("secondString");

  private final Subscriber firstIntSubscriber = createIntSubscriber("firstInt");
  private final Subscriber secondIntSubscriber = createIntSubscriber("secondInt");
  private final Subscriber thirdIntSubscriber = createIntSubscriber("thirdInt");

  private final List<Subscriber> stringSubscribers =
      List.of(firstStringSubscriber, secondStringSubscriber);

  private final List<Subscriber> intSubscribers =
      List.of(firstIntSubscriber, secondIntSubscriber, thirdIntSubscriber);

  private Dispatcher dispatcher;

  @AfterEach
  void tearDown() {
    dispatcher = null;
  }

  @Test
  void testImmediateDispatcher() {
    dispatcher = Dispatchers.immediate();
    dispatcher.dispatchEvent(1337, intSubscribers);

    assertThat(dispatchedSubscribers)
        .containsExactly(
            firstIntSubscriber, firstStringSubscriber, secondStringSubscriber,
            secondIntSubscriber, firstStringSubscriber, secondStringSubscriber,
            thirdIntSubscriber, firstStringSubscriber, secondStringSubscriber
        );
  }

  @Test
  void testQueuedDispatcher() {
    dispatcher = Dispatchers.perThreadQueued();
    dispatcher.dispatchEvent(1337, intSubscribers);

    assertThat(dispatchedSubscribers)
        .containsExactly(
            firstIntSubscriber, secondIntSubscriber, thirdIntSubscriber,
            firstStringSubscriber, secondStringSubscriber,
            firstStringSubscriber, secondStringSubscriber,
            firstStringSubscriber, secondStringSubscriber
        );
  }

  private Subscriber createIntSubscriber(String name) {
    return new IntegerSubscriber(name);
  }

  private Subscriber createStringSubscriber(String name) {
    return new StringSubscriber(name);
  }

  private final class IntegerSubscriber implements Subscriber {

    private final String name;

    private IntegerSubscriber(String name) {
      this.name = name;
    }

    @Override
    public void call(Object event) {
      dispatchedSubscribers.add(this);
      dispatcher.dispatchEvent("event", stringSubscribers);
    }

    @Override
    public Class<?> getEventType() {
      return Integer.class;
    }

    @Override
    public String toString() {
      return name;
    }
  }

  private final class StringSubscriber implements Subscriber {

    private final String name;

    private StringSubscriber(String name) {
      this.name = name;
    }

    @Override
    public void call(Object event) {
      dispatchedSubscribers.add(this);
    }

    @Override
    public Class<?> getEventType() {
      return String.class;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
