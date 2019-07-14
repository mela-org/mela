package com.github.stupremee.mela.event;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.stupremee.mela.event.annotations.Subscribe;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
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

  private List<Object> calledListener;
  private EventBus eventBus;
  private Injector injector;

  @BeforeAll
  void setUp() {
    this.calledListener = new ArrayList<>();
    Module calledListenerModule = new AbstractModule() {
      @Override
      protected void configure() {
        bind(new TypeLiteral<List<Object>>() {
        }).toInstance(calledListener);
      }
    };
    Injector injector = Guice.createInjector(EventModule.create(), calledListenerModule);
    this.eventBus = injector.getInstance(EventBus.class);
    this.injector = injector;
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
    eventBus.post(new TestEvent("secondEvent", 2));

    AutoSubscribeListener autoSubscribeListener = injector.getInstance(AutoSubscribeListener.class);
    assertThat(calledListener).containsExactly(firstListener, firstListener, autoSubscribeListener);
  }

  private final class TestListener {

    @Subscribe
    void eventCalled(TestEvent event) {
      calledListener.add(this);
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
