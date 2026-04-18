package com.group26.smart_home_system.exception;

import java.io.Serial;

public class ActuatorNotFoundException extends Exception {

  @Serial
  private static final long serialVersionUID = 1L;

  public ActuatorNotFoundException(String message) {
    super(message);
  }

}
