package com.group26.smart_home_system.exception;

public class SensorNotFoundException extends RuntimeException {
  public SensorNotFoundException(String message) {
    super(message);
  }
}
