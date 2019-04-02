package com.github.stupremee.mela.event;

import com.github.stupremee.mela.util.Loggers;
import discord4j.core.event.domain.Event;
import io.vavr.control.Option;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
@SuppressWarnings("unchecked")
public class DefaultEventDispatcher implements EventDispatcher {

  private static final Logger log = Loggers.getLogger("EventDispatcher");
  private final UnicastProcessor<Event> processor;
  private final FluxSink<Event> sink;
  private final Scheduler scheduler;
  private Map<Class<? extends Event>, Map<Object, List<Method>>> methods = new java.util.HashMap<>();
  private Set<Object> listeners = new HashSet<>();

  private DefaultEventDispatcher() {
    this.processor = UnicastProcessor.create();
    this.scheduler = Schedulers.fromExecutor(Executors.newCachedThreadPool());
    this.sink = processor.sink();
  }

  @Override
  public void call(Event event) {
    sink.next(event);

    Option<Class<? extends Event>> eventClass = Option.of(event.getClass());

    do {
      Map<Object, List<Method>> listeners = methods.get(eventClass.get());
      if (listeners != null) {
        listeners.forEach((key, value) -> value.forEach(method ->
        {
          try {
            method.setAccessible(true);
            method.invoke(key, event);
          } catch (IllegalAccessException | InvocationTargetException error) {
            log.error("Failed to access event listener method.", error);
          } catch (Throwable error) {
            log.error("One of the event listeners threw an unknown exception.", error);
          }
        }));
      }
      eventClass =
          eventClass.filter(c -> c == Event.class).isEmpty() ? Option.none()
              : Option.of((Class<? extends Event>) eventClass.get().getSuperclass());
    }
    while (!eventClass.isEmpty());
  }

  @Override
  public <EventT extends Event> Flux<EventT> on(Class<EventT> eventClass) {
    return processor.publishOn(scheduler)
        .ofType(eventClass);
  }

  @Override
  public void register(Object listener) {
    if (listeners.add(listener)) {
      refreshMethods();
    }
  }

  @Override
  public void unregister(Object listener) {
    if (listeners.remove(listener)) {
      refreshMethods();
    }
  }

  private void refreshMethods() {
    methods.clear();

    io.vavr.collection.List.ofAll(listeners).filter(o -> !(o instanceof Class))
        .forEach(o -> {
          var list = io.vavr.collection.List.of(o.getClass().getDeclaredMethods());

          list = list.filter(m -> m.isAnnotationPresent(Subscribe.class));
          list = list.filter(m -> m.getParameterTypes().length == 1 && Event.class
              .isAssignableFrom(m.getParameterTypes()[0]));
          list.forEach(m -> {
            @SuppressWarnings("unused")
            Class<? extends Event> event = (Class<? extends Event>) m.getParameterTypes()[0];
            if (!methods.containsKey(event)) {
              methods.put(event, new HashMap<>());
            }

            if (!methods.get(event).containsKey(o)) {
              methods.get(event).put(o, new ArrayList<>());
            }

            methods.get(event).get(o).add(m);
          });
        });
  }

  public static EventDispatcher create() {
    return new DefaultEventDispatcher();
  }
}
