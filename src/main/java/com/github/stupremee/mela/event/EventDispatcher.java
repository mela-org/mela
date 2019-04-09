package com.github.stupremee.mela.event;

import discord4j.core.event.domain.Event;
import javax.annotation.Nonnull;
import reactor.core.publisher.Flux;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
@SuppressWarnings("unused")
public interface EventDispatcher {

  /**
   * Creates a new event dispatcher.
   *
   * @return The new event dispatcher
   */
  @Nonnull
  static EventDispatcher vanilla() {
    return new VanillaEventDispatcher();
  }

  /**
   * Calls a event, invokes all methods with {@link Subscribe} annotation and executes all
   * subscribed {@link Flux Flux's}.
   */
  void call(@Nonnull Event event);

  /**
   * Retrieves a {@link Flux} with elements of the given {@link Event} type.
   *
   * @param eventClass the event class to obtain events from
   * @param <EventT> the type of the event class
   * @return a new {@link Flux} with the requested events
   */
  @Nonnull
  <EventT extends Event> Flux<EventT> on(@Nonnull Class<EventT> eventClass);

  /**
   * Registers a new event listener like the JDA Style by using the {@link Subscribe} annotation.
   *
   * @param listener The listener
   */
  void register(@Nonnull Object listener);
}
