package com.group26.smart_home_system.exception;

import java.io.Serial;

public class LocationNotFoundException extends Exception {

  @Serial
  private static final long serialVersionUID = 1L;

  public LocationNotFoundException(String message) {
    super(message);
  }

}
