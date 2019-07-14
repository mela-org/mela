package com.github.stupremee.mela.event.dispatchers;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.stupremee.mela.event.Dispatcher;
import com.github.stupremee.mela.event.subscriber.Subscriber;

/**
 * This dispatcher calls the given subscribers immediately as they are posted. The idea for this
 * dispatcher comes from Guava (https://github.com/google/guava/blob/master/guava/src/com/google/common/eventbus/Dispatcher.java#L179)
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
    checkNotNull(event, "event can't be null.");
    checkNotNull(subscribers, "subscribers can't be null.");

    for (Subscriber subscriber : subscribers) {
      subscriber.call(event);
    }
  }

  static ImmediateDispatcher getInstance() {
    return Lazy.INSTANCE;
  }
}
