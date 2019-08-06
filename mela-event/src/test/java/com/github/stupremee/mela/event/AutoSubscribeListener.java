package com.github.stupremee.mela.event;

import com.github.stupremee.mela.event.annotations.AutoSubscriber;
import com.github.stupremee.mela.event.annotations.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 14.07.19
 */
@AutoSubscriber
@SuppressWarnings("ALL")
final class AutoSubscribeListener {

  private final List<Object> calledListener;

  @Inject
  AutoSubscribeListener(
      List<Object> calledListener) {
    this.calledListener = calledListener;
  }

  @Subscribe
  void eventCalled(TestEvent event) {
    if (event.getNumber() == 1) {
      this.calledListener.add(this);
    }
  }

  void methodShouldNotBeCalled(TestEvent event) {
    throw new AssertionError();
  }

  @Subscribe
  void tooManyParameters(TestEvent event, String name) {
    throw new AssertionError();
  }

  @Subscribe
  void tooLessParameters() {
    throw new AssertionError();
  }

  @Override
  public boolean equals(Object obj) {
    return true;
  }
}

