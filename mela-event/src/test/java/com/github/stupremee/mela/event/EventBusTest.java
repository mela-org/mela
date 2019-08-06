package com.github.stupremee.mela.event;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.stupremee.mela.event.annotations.Subscribe;
import com.github.stupremee.mela.event.subscriber.Subscriber;
import com.google.inject.*;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Module;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 11.07.19
 */
@TestInstance(Lifecycle.PER_CLASS)
final class EventBusTest {

  private final TestListener firstListener = new TestListener();
  private final TestListener secondListener = new TestListener();

  private AutoSubscribeListener autoSubscribeListener;
  private List<Object> calledListener;
  private EventBus eventBus;

  @BeforeAll
  void setUp() {
    this.calledListener = new ArrayList<>();
    this.autoSubscribeListener = new AutoSubscribeListener(calledListener);
    Module calledListenerModule = new AbstractModule() {
      @Override
      protected void configure() {
        bind(AutoSubscribeListener.class)
            .toInstance(autoSubscribeListener);
      }
    };
    Injector injector = Guice.createInjector(EventModule.create(), calledListenerModule);
    this.eventBus = injector.getInstance(EventBus.class);
  }

  @AfterAll
  void tearDown() {
    this.eventBus = null;
  }

  @Test
  void testRegister() {
    eventBus.register(firstListener);
    eventBus.register(secondListener);

    eventBus.registerFromClasspath();
  }

  @Test
  void testUnregister() {
    eventBus.unregister(secondListener);
  }

  @Test
  void testPostEvents() {
    eventBus.post(new TestEvent("firstEvent", 1));

    assertThat(calledListener).contains(this.firstListener, this.autoSubscribeListener);
  }

  private final class TestListener {

    @Inject
    TestListener() {

    }

    @Subscribe
    void eventCalled(TestEvent event) {
      if (event.getNumber() == 1) {
        calledListener.add(this);
      }
    }

    void methodShouldNotBeCalled(TestEvent event) {
      throw new AssertionError();
    }

    @Subscribe
    void tooManyParameters(TestEvent event, String name) {
      throw new AssertionError();
    }

    @Subscribe
    void tooLessParameters() {
      throw new AssertionError();
    }
  }
}
