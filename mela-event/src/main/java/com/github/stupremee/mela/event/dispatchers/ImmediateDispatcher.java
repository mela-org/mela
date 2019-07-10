package com.github.stupremee.mela.event.dispatchers;

import com.github.stupremee.mela.event.Dispatcher;
import com.github.stupremee.mela.event.Subscriber;

/**
 * This dispatcher calls the given subscribers immediately as they are posted.
 *
 * @author Stu (https://github.com/Stupremee)
 * @since 10.07.19
 */
final class ImmediateDispatcher implements Dispatcher {

  private static final class Lazy {

    private static final ImmediateDispatcher INSTANCE = new ImmediateDispatcher();
  }

  private ImmediateDispatcher() {

  }

  @Override
  public void dispatchEvent(Object event, Iterable<Subscriber> subscribers) {
    for (Subscriber subscriber : subscribers) {
      subscriber.call(event);
    }
  }

  static ImmediateDispatcher getInstance() {
    return Lazy.INSTANCE;
  }
}
