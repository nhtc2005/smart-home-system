package com.group26.smart_home_system.exception;

public class ActuatorNotFoundException extends RuntimeException {
  public ActuatorNotFoundException(String message) {
    super(message);
  }
}
