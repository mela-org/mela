package com.github.stupremee.mela.event;

import com.github.stupremee.mela.event.listener.GenericListener;
import com.github.stupremee.mela.event.listener.Listener;
import java.util.function.Consumer;

/**
 * This interface will create a {@link Subscriber} on different ways.
 *
 * @author Stu (https://github.com/Stupremee)
 * @since 14.07.19
 */
public interface SubscriberFactory {

  /**
   * Creates a {@link Subscriber} by searching all methods in the given listener which are annotated
   * with {@link com.github.stupremee.mela.event.annotations.Subscribe}.
   */
  Subscriber fromListener(Object listener);

  /**
   * Creates a {@link Subscriber} from the given {@code listener}.
   */
  Subscriber fromListener(Listener listener);

  /**
   * Creates a {@link Subscriber} from the given {@code listener}.
   */
  <T> Subscriber fromListener(GenericListener<T> listener);

  /**
   * Creates a {@link Subscriber} from the given {@link Consumer callback} which will be called on
   * every event.
   */
  Subscriber fromCallback(Consumer<Object> callback);

  /**
   * Creates a {@link Subscriber} from the given {@link Consumer callback} which will be called on
   * every event of the given {@code eventType}.
   */
  <T> Subscriber fromCallback(Class<T> eventType, Consumer<T> callback);
}
