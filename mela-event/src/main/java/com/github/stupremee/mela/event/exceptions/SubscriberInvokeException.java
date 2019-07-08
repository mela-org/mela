package com.github.stupremee.mela.event.exceptions;

import com.github.stupremee.mela.event.Subscriber;

/**
 * This exception is thrown when the {@link com.github.stupremee.mela.event.Subscriber} fails to
 * invoke his method.
 *
 * @author Stu (https://github.com/Stupremee)
 * @since 08.07.19
 */
public final class SubscriberInvokeException extends RuntimeException {

  private final Subscriber subscriber;

  private SubscriberInvokeException(String message, Throwable cause, Subscriber subscriber) {
    super(message, cause);
    this.subscriber = subscriber;
  }

  /**
   * Returns the {@link Subscriber} where the error occurred.
   */
  public Subscriber getSubscriber() {
    return subscriber;
  }

  /**
   * Creates  a new {@link SubscriberInvokeException} with the given {@link Subscriber} and the
   * given {@link Throwable cause}.
   */
  public static SubscriberInvokeException create(Subscriber subscriber, Throwable cause) {
    return new SubscriberInvokeException("Failed to call a subscriber.", cause, subscriber);
  }
}
