package com.github.stupremee.mela.event.listener;

/**
 * This interface is a listener which listens to a specific event type which.
 *
 * @author Stu (https://github.com/Stupremee)
 * @since 14.07.19
 */
public interface GenericListener<EventT> {

  /**
   * This methods get called when a event with the type {@code EventT} was posted.
   */
  void onEvent(EventT event);

  /**
   * Returns the class of the event.
   */
  Class<EventT> getEventType();
}
