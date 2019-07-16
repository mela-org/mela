package com.github.stupremee.mela.event.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.stupremee.mela.event.subscriber.Subscriber;
import com.google.common.base.Objects;
import com.google.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 16.07.19
 */
final class EventMethodsSubscriber implements Subscriber {

  private final List<EventMethod> methods;

  @Inject
  EventMethodsSubscriber(List<EventMethod> methods) {
    this.methods = methods;
  }

  @Override
  public void call(Object event) {
    checkNotNull(event, "event can't be null.");
    Class<?> eventClass = event.getClass();
    methods.stream()
        .filter(method -> method.type.isAssignableFrom(eventClass))
        .forEach(method -> invokeEventMethod(method, event));
  }

  @Override
  public boolean supportsType(Class<?> type) {
    return methods.stream()
        .map(method -> method.type)
        .anyMatch(clazz -> clazz.isAssignableFrom(type));
  }

  private void invokeEventMethod(EventMethod eventMethod, Object event) {
    Method method = eventMethod.method;
    try {
      method.invoke(eventMethod.listener, event);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(
          "Can't access event method " + method + " in listener " + eventMethod.listener, e);
      // TODO: 16.07.19 Better error handling
    } catch (InvocationTargetException e) {
      throw new RuntimeException(
          "An exception was thrown in method " + method + " in listener " + eventMethod.listener,
          e);
      // TODO: 16.07.19 Better error handling
    } catch (Throwable e) {
      throw new RuntimeException("Unknown error occurred", e);
      // TODO: 16.07.19 Better error handling
    }
  }

  static final class EventMethod {

    private final Method method;
    private final Class<?> type;
    private final Object listener;

    EventMethod(Method method, Class<?> type, Object listener) {
      this.method = method;
      this.type = type;
      this.listener = listener;
    }

    public Method getMethod() {
      return method;
    }

    public Class<?> getType() {
      return type;
    }

    public Object getListener() {
      return listener;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(method, type, listener);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }

      if (!(obj instanceof EventMethod)) {
        return false;
      }

      EventMethod that = (EventMethod) obj;
      return Objects.equal(that.listener, listener)
          && Objects.equal(that.method, method)
          && Objects.equal(that.type, type);
    }
  }
}
