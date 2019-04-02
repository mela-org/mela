package com.github.stupremee.mela.event;

import static org.junit.Assert.assertEquals;

import discord4j.core.event.domain.Event;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Before;
import org.junit.Test;

public class EventDispatcherTest {

  private DefaultEventDispatcher dispatcher;

  @Before
  public void setUp() {
    this.dispatcher = (DefaultEventDispatcher) DefaultEventDispatcher.create();
  }

  @Test
  public void registerTest() {
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

      public void failed(TestEvent event, String test) {
        throw new AssertionError();
      }

    });

    dispatcher.call(new TestEvent("YUS"));
    assertEquals("YUS", test.get());
  }

  @Test
  public void reactorTest() {
    AtomicReference<String> test = new AtomicReference<>();
    dispatcher.on(TestEvent.class)
        .doOnNext(event -> test.set(event.testing))
        .subscribe();

    dispatcher.call(new TestEvent("Hello"));
    assertEquals("Hello", test.get());

  }

  private static class TestEvent extends Event {

    private final String testing;

    private TestEvent(String testing) {
      super(null);
      this.testing = testing;
    }
  }
}