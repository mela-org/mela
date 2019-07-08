package com.github.stupremee.mela.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriberRegistryTest {

  private SubscriberRegistry registry;

  @BeforeEach
  void setUp() {
    Injector injector = Guice.createInjector(EventModule.create());
    this.registry = injector.getInstance(SubscriberRegistry.class);
  }

  @AfterEach
  void tearDown() {
    registry = null;
  }

  @Test
  void registerTest() {
    Subscriber stringSubscriber = new ClassSubscriber(String.class);
    Subscriber charSequenceSubscriber = new ClassSubscriber(CharSequence.class);
    Subscriber longSubscriber = new ClassSubscriber(long.class);
    Subscriber intSubscriber = new ClassSubscriber(int.class);
    registry.register(stringSubscriber);
    registry.register(intSubscriber);
    registry.register(longSubscriber);
    registry.register(charSequenceSubscriber);

    assertThatIllegalArgumentException().isThrownBy(() -> registry.register(stringSubscriber));
  }

  @Test
  void unregisterTest() {
    Subscriber intSubscriber = new ClassSubscriber(int.class);
    Subscriber boolSubscriber = new ClassSubscriber(boolean.class);

    assertThat(registry.unregister(intSubscriber))
        .isTrue();
    assertThat(registry.unregister(boolSubscriber))
        .isFalse();
  }

  @Test
  void getSubscribersForEventTest() {
    Set<Subscriber> longSubscribers = registry.getSubscribersForEvent(123L);
    Subscriber firstLongSubscriber = longSubscribers.iterator().next();

    Subscriber stringSubscriber = new ClassSubscriber(String.class);
    Subscriber charSequenceSubscriber = new ClassSubscriber(CharSequence.class);
    Set<Subscriber> stringSubscribers = registry.getSubscribersForEvent("some");

    assertThat(firstLongSubscriber).isNotNull();
    assertThat(firstLongSubscriber.getEventType()).isExactlyInstanceOf(long.class);

    assertThat(stringSubscribers).isNotEmpty();
    assertThat(stringSubscribers)
        .containsExactlyInAnyOrder(stringSubscriber, charSequenceSubscriber);
  }

  static final class ClassSubscriber implements Subscriber {

    private final Class<?> clazz;

    private ClassSubscriber(Class<?> clazz) {
      this.clazz = clazz;
    }

    @Override
    public void call(Object event) {

    }

    @Override
    public Class<?> getEventType() {
      return clazz;
    }

    @Override
    public Method getMethod() {
      return null;
    }

    @Override
    public Object getListener() {
      return null;
    }

    @Override
    public int hashCode() {
      return clazz.hashCode();
    }

    @Override
    public boolean equals(Object o) {
      if (o == null) {
        return false;
      }

      if (!(o instanceof ClassSubscriber)) {
        return false;
      }

      ClassSubscriber that = (ClassSubscriber) o;
      return Objects.equals(this.clazz, that.clazz);
    }
  }
}