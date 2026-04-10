package com.group26.smart_home_system.exception;

import java.io.Serial;

public class UnauthorizedException extends Exception {

  @Serial
  private static final long serialVersionUID = 1L;

  public UnauthorizedException(String message) {
    super(message);
  }

}
