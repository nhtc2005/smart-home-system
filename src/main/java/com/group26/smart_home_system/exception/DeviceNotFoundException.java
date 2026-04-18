package com.group26.smart_home_system.exception;

public class DeviceNotFoundException extends RuntimeException {
  public DeviceNotFoundException(String message) {
    super(message);
  }
}
