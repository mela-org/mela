package com.github.stupremee.mela.event.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.stupremee.mela.event.annotations.AutoSubscriber;
import com.github.stupremee.mela.event.subscriber.Subscriber;
import com.github.stupremee.mela.event.subscriber.SubscriberFactory;
import com.github.stupremee.mela.event.subscriber.SubscriberRegistry;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 09.07.19
 */
public final class DefaultSubscriberRegistry implements SubscriberRegistry {

  private final List<Subscriber> subscribers;
  private final SubscriberFactory subscriberFactory;
  private final Injector injector;

  @Inject
  DefaultSubscriberRegistry(
      SubscriberFactory subscriberFactory,
      Injector injector) {
    this.subscriberFactory = subscriberFactory;
    this.subscribers = new ArrayList<>();
    this.injector = injector;
  }

  @Override
  public void register(Subscriber subscriber) {
    checkNotNull(subscriber, "subscriber can't be null.");
    if (subscribers.contains(subscriber)) {
      throw new IllegalArgumentException(
          "The Subscriber " + subscriber + " is already registered.");
    }
    subscribers.add(subscriber);
  }

  @Override
  public boolean unregister(Subscriber subscriber) {
    checkNotNull(subscriber, "subscriber can't be null.");
    return subscribers.remove(subscriber);
  }

  @Override
  public Set<Subscriber> getSubscribersForEvent(Object event) {
    checkNotNull(event, "event can't be null.");
    Class<?> eventClass = event.getClass();
    return subscribers.stream()
        .filter(subscriber -> subscriber.supportsType(eventClass))
        .collect(Collectors.toUnmodifiableSet());
  }

  @Override
  public void registerFromClasspath() {
    scanForSubscriberClasses()
        .map(injector::getInstance)
        .map(subscriberFactory::fromListener)
        .forEach(this::register);
  }

  private Stream<Class<?>> scanForSubscriberClasses() {
    ClassGraph classGraph = new ClassGraph()
        .ignoreClassVisibility()
        .enableAnnotationInfo()
        .enableClassInfo();

    try (ScanResult result = classGraph.scan()) {
      return result.getClassesWithAnnotation(AutoSubscriber.class.getName())
          .loadClasses(true)
          .stream();
    }
  }
}
