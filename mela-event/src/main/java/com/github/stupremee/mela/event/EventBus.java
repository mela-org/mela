package com.github.stupremee.mela.event;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 08.07.19
 */
public interface EventBus {

  void post(Object event);

  void register(Object listener);

  void unregister(Object listener);
}
