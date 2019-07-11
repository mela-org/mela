package com.github.stupremee.mela.event.dispatchers;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.stupremee.mela.event.Dispatcher;
import com.github.stupremee.mela.event.Subscriber;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * This dispatcher comes from Guava (https://github.com/google/guava/blob/master/guava/src/com/google/common/eventbus/Dispatcher.java#L73)
 *
 * @author Stu (https://github.com/Stupremee)
 * @since 10.07.19
 */
final class PerThreadQueuedDispatcher implements Dispatcher {

  private final ThreadLocal<Queue<Event>> eventQueue;
  private final ThreadLocal<Boolean> dispatching;

  private PerThreadQueuedDispatcher() {
    this.eventQueue = ThreadLocal.withInitial(ArrayDeque::new);
    this.dispatching = ThreadLocal.withInitial(() -> false);
  }

  @Override
  public void dispatchEvent(Object event, Iterable<Subscriber> subscribers) {
    checkNotNull(event, "event can't be null.");
    checkNotNull(subscribers, "subscribers can't be null.");

    if (!subscribers.iterator().hasNext()) {
      return;
    }

    Queue<Event> queue = eventQueue.get();
    queue.offer(new Event(event, subscribers));

    if (!dispatching.get()) {
      dispatching.set(true);
      dispatchEventsFromQueue();
    }
  }

  private void dispatchEventsFromQueue() {
    try {
      Queue<Event> events = eventQueue.get();
      Event next;
      while ((next = events.poll()) != null) {
        next.call();
      }
    } finally {
      dispatching.remove();
      eventQueue.remove();
    }
  }

  private static final class Event {

    private final Object event;
    private final Iterable<Subscriber> subscribers;

    private Event(Object event,
        Iterable<Subscriber> subscribers) {
      this.event = event;
      this.subscribers = subscribers;
    }

    private void call() {
      for (Subscriber subscriber : subscribers) {
        subscriber.call(event);
      }
    }
  }

  static PerThreadQueuedDispatcher create() {
    return new PerThreadQueuedDispatcher();
  }
}
