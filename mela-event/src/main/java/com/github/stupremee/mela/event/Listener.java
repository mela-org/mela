package com.github.stupremee.mela.event;

/**
 * This listener listens to all events.
 *
 * @author Stu (https://github.com/Stupremee)
 * @since 14.07.19
 */
@FunctionalInterface
public interface Listener {

  /**
   * This method gets called when a event was posted.
   */
  void onEvent(Object event);

}
