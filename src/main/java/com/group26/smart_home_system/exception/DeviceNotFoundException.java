package com.group26.smart_home_system.exception;

import java.io.Serial;

public class DeviceNotFoundException extends Exception {

  @Serial
  private static final long serialVersionUID = 1L;

  public DeviceNotFoundException(String message) {
    super(message);
  }

}
