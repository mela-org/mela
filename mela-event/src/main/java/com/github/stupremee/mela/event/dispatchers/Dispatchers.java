package com.github.stupremee.mela.event.dispatchers;

import com.github.stupremee.mela.event.Dispatcher;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 10.07.19
 */
public final class Dispatchers {

  private Dispatchers() {

  }

  /**
   * Returns a dispatcher that queues events that are posted reentrant on a thread that is already
   * dispatching an event.
   */
  public static Dispatcher perThreadQueued() {
    return PerThreadQueuedDispatcher.create();
  }

  /**
   * Returns a dispatcher which immediately dispatch events as they're posted.
   */
  public static Dispatcher immediate() {
    return ImmediateDispatcher.getInstance();
  }

}
