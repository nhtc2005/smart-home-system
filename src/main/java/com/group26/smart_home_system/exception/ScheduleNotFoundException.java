package com.group26.smart_home_system.exception;

import java.io.Serial;

public class ScheduleNotFoundException extends Exception {

  @Serial
  private static final long serialVersionUID = 1L;

  public ScheduleNotFoundException(String message) {
    super(message);
  }

}
