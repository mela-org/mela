package com.github.stupremee.mela.event;

import static org.junit.Assert.assertEquals;

import discord4j.core.event.domain.Event;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Before;
import org.junit.Test;

public class EventDispatcherTest {

  private EventDispatcher dispatcher;

  @Before
  public void setUp() {
    this.dispatcher = EventDispatcher.vanilla();
  }

  @Test
  public void registerTest() throws InterruptedException {
    AtomicReference<String> test = new AtomicReference<>();

    dispatcher.register(new Object() {

      @Subscribe
      public void onTestEvent(TestEvent event) {
        test.set(event.testing);
      }

      @Subscribe
      public void moreTest(TestEvent event, String test) {
        throw new AssertionError();
      }

      public void failed(TestEvent event) {
        throw new AssertionError();
      }

    });

    dispatcher.call(new TestEvent("YUS"));
    Thread.sleep(5); // Some sleep because it takes some time to call the method.
    assertEquals("YUS", test.get());
  }

  private static class TestEvent extends Event {

    private final String testing;

    private TestEvent(String testing) {
      super(null);
      this.testing = testing;
    }
  }
}