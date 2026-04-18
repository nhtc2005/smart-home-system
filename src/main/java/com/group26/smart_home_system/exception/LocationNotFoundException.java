package com.group26.smart_home_system.exception;

import java.io.Serial;

public class LocationNotFound extends Exception {

  @Serial
  private static final long serialVersionUID = 1L;

  public LocationNotFound(String message) {
    super(message);
  }
}
