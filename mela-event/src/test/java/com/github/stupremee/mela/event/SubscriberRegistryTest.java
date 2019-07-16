package com.github.stupremee.mela.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.stupremee.mela.event.subscriber.Subscriber;
import com.github.stupremee.mela.event.subscriber.SubscriberRegistry;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
final class SubscriberRegistryTest {

  private SubscriberRegistry registry;

  @BeforeAll
  void setUp() {
    Injector injector = Guice.createInjector(EventModule.create());
    this.registry = injector.getInstance(SubscriberRegistry.class);
  }

  @AfterAll
  void tearDown() {
    registry = null;
  }

  @Test
  void testRegister() {
    Subscriber stringSubscriber = createSubscriber(String.class);
    Subscriber charSequenceSubscriber = createSubscriber(CharSequence.class);
    Subscriber longSubscriber = createSubscriber(Long.class);
    Subscriber boolSubscriber = createSubscriber(Boolean.class);

    registry.register(stringSubscriber);
    registry.register(longSubscriber);
    registry.register(boolSubscriber);
    registry.register(charSequenceSubscriber);

    assertThatIllegalArgumentException().isThrownBy(() -> registry.register(stringSubscriber));
    assertThatNullPointerException().isThrownBy(() -> registry.register(null));
  }

  @Test
  void testUnregister() {
    Subscriber intSubscriber = createSubscriber(int.class);
    Subscriber boolSubscriber = createSubscriber(Boolean.class);

    assertThat(registry.unregister(intSubscriber))
        .isFalse();
    assertThat(registry.unregister(boolSubscriber))
        .isTrue();
    assertThatNullPointerException().isThrownBy(() -> registry.unregister(null));
  }

  @Test
  void testGetSubscribersForEvent() {
    Set<Subscriber> longSubscribers = registry.getSubscribersForEvent(123L);
    Set<Subscriber> stringSubscribers = registry.getSubscribersForEvent("some");

    Subscriber stringSubscriber = new ClassSubscriber(String.class);
    Subscriber charSequenceSubscriber = new ClassSubscriber(CharSequence.class);
    Subscriber firstLongSubscriber = longSubscribers.iterator().next();

    assertNotNull(firstLongSubscriber);
    assertThat(firstLongSubscriber.supportsType(Long.class)).isTrue();

    assertThat(stringSubscribers).isNotEmpty();
    assertThat(stringSubscribers)
        .containsExactlyInAnyOrder(stringSubscriber, charSequenceSubscriber);
    assertThatNullPointerException().isThrownBy(() -> registry.getSubscribersForEvent(null));
  }

  private Subscriber createSubscriber(Class<?> type) {
    return new ClassSubscriber(type);
  }

  static final class ClassSubscriber implements Subscriber {

    private final Class<?> clazz;

    private ClassSubscriber(Class<?> clazz) {
      this.clazz = clazz;
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

    @Override
    public void call(Object event) {

    }

    @Override
    public boolean supportsType(Class<?> type) {
      return clazz.isAssignableFrom(type);
    }
  }
}