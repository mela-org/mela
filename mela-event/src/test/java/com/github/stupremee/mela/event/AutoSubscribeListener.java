package com.github.stupremee.mela.event;

import com.github.stupremee.mela.event.annotations.AutoSubscriber;
import com.github.stupremee.mela.event.annotations.Subscribe;
import java.util.List;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 14.07.19
 */
@AutoSubscriber
@SuppressWarnings("ALL")
final class AutoSubscribeListener {

  private final List<Object> calledListener;
  private boolean called = false;

  AutoSubscribeListener(
      List<Object> calledListener) {
    this.calledListener = calledListener;
  }

  @Subscribe
  void eventCalled(TestEvent event) {
    if (!called) {
      this.calledListener.add(this);
      called = true;
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
  public int hashCode() {
    return 42;
  }

  @Override
  public boolean equals(Object obj) {
    return true;
  }
}

