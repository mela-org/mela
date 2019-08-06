package com.github.stupremee.mela.event;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.stupremee.mela.event.annotations.EventExecutor;
import com.github.stupremee.mela.event.dispatchers.Dispatchers;
import com.github.stupremee.mela.event.internal.DefaultEventBus;
import com.github.stupremee.mela.event.internal.DefaultSubscriberFactory;
import com.github.stupremee.mela.event.internal.DefaultSubscriberRegistry;
import com.github.stupremee.mela.event.subscriber.SubscriberFactory;
import com.github.stupremee.mela.event.subscriber.SubscriberRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.name.Names;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 08.07.19
 */
public final class EventModule extends AbstractModule {

  private final List<Consumer<Binder>> customBindings;
  private final Dispatcher dispatcher;
  private final Executor eventExecutor;

  private EventModule(Builder builder) {
    this.customBindings = builder.customBindings;
    this.dispatcher = builder.dispatcher;
    this.eventExecutor = builder.eventExecutor;
  }

  @Override
  protected void configure() {
    bind(SubscriberRegistry.class)
        .to(DefaultSubscriberRegistry.class)
        .in(Singleton.class);

    bind(Dispatcher.class)
        .toInstance(dispatcher);

    bind(Executor.class)
        .annotatedWith(EventExecutor.class)
        .toInstance(eventExecutor);

    bind(SubscriberFactory.class)
        .to(DefaultSubscriberFactory.class)
        .in(Singleton.class);

    bind(EventBus.class)
        .to(DefaultEventBus.class);

    customBindings.forEach(function -> function.accept(binder()));
  }

  /**
   * Creates a new {@link EventModule} with a Immediate Dispatcher.
   */
  public static Module create() {
    return builder().build();
  }

  /**
   * Creates a new {@link Builder} which can be used to create a new {@link EventModule}.
   */
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private final List<Consumer<Binder>> customBindings = new ArrayList<>();
    private Dispatcher dispatcher = Dispatchers.perThreadQueued();
    private Executor eventExecutor = Executors.newCachedThreadPool();

    private Builder() {

    }

    /**
     * Sets the {@link Executor} which will be used to post events. Default executor is {@link
     * Executors#newCachedThreadPool()}
     */
    public Builder eventExecutor(Executor executor) {
      this.eventExecutor = executor;
      return this;
    }

    /**
     * Sets the {@link Dispatcher} which will be used to dispatch events. Default Dispatcher is
     * {@link Dispatchers#perThreadQueued()}
     */
    public Builder dispatcher(Dispatcher dispatcher) {
      this.dispatcher = dispatcher;
      return this;
    }

    /**
     * This method will bind a new event bus to the {@code @Named(yourGivenName)} annotation.
     */
    public Builder customEventBus(String name) {
      checkNotNull(name, "name can't be null.");
      this.customBindings.add(bindEventBus(binding -> binding.annotatedWith(Names.named(name))));
      return this;
    }

    /**
     * Binds a new {@link EventBus} to the given binding annotation.
     */
    public Builder customEventBus(Class<? extends Annotation> annotation) {
      checkNotNull(annotation, "annotation can't be null.");
      this.customBindings.add(bindEventBus(binding -> binding.annotatedWith(annotation)));
      return this;
    }

    /**
     * Builds a new {@link EventModule}.
     */
    public EventModule build() {
      checkNotNull(dispatcher, "dispatcher can't be null.");
      checkNotNull(eventExecutor, "eventExecutor can't be null.");
      return new EventModule(this);
    }

    private Consumer<Binder> bindEventBus(
        Function<AnnotatedBindingBuilder<EventBus>, LinkedBindingBuilder<EventBus>> customBinding) {
      return binder -> {
        AnnotatedBindingBuilder<EventBus> builder = binder.bind(EventBus.class);
        LinkedBindingBuilder<EventBus> linkedBindingBuilder = customBinding.apply(builder);
        linkedBindingBuilder.to(DefaultEventBus.class).asEagerSingleton();
      };
    }
  }
}
