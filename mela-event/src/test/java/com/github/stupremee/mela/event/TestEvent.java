package com.github.stupremee.mela.event;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 11.07.19
 */
final class TestEvent {

  private final String event;
  private final int number;

  TestEvent(String event, int number) {
    this.event = event;
    this.number = number;
  }

  public int getNumber() {
    return number;
  }

  public String getEvent() {
    return event;
  }
}
