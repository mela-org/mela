package com.github.stupremee.mela.event;

import com.github.stupremee.mela.util.Loggers;
import discord4j.core.event.domain.Event;
import io.vavr.control.Try;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
public class VanillaEventDispatcher implements EventDispatcher {

  private static final Logger LOG = Loggers.getLogger("EventDispatcher");
  private final UnicastProcessor<Event> processor;
  private final FluxSink<Event> sink;
  private final Scheduler scheduler;

  VanillaEventDispatcher() {
    this.processor = UnicastProcessor.create();
    this.scheduler = Schedulers.fromExecutor(Executors.newCachedThreadPool());
    this.sink = processor.sink();
  }

  @Override
  public void call(Event event) {
    sink.next(event);
  }

  @Override
  public <EventT extends Event> Flux<EventT> on(Class<EventT> eventClass) {
    return processor.publishOn(scheduler)
        .ofType(eventClass);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void register(Object listener) {
    Stream.of(listener.getClass().getDeclaredMethods())
        .filter(this::validateMethod)
        .map(method -> Tuples.of(method, method.getParameterTypes()[0]))
        .map(tuple -> Tuples.of(tuple.getT1(), (Class<? extends Event>) tuple.getT2()))
        .forEach(tuple ->
            on((Class<? extends Event>) tuple.getT2())
                .subscribe(event ->
                    Try.run(() -> tuple.getT1().invoke(listener, (Event) event))
                        .onFailure(t -> LOG.error("Failed to invoke event listener method.", t))));
  }

  private boolean validateMethod(Method method) {
    return method.isAnnotationPresent(Subscribe.class)
        && (method.getParameterTypes().length == 1 && Event.class
        .isAssignableFrom(method.getParameterTypes()[0]));
  }
}
