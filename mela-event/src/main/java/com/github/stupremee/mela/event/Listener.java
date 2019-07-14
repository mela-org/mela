package com.github.stupremee.mela.event;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 14.07.19
 */
public interface Listener {

  /**
   * This method gets called when a event was posted
   */
  void onEvent(Object event);

}
