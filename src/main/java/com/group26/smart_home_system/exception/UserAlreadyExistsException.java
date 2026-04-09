package com.group26.smart_home_system.exception;

import java.io.Serial;

public class UserAlreadyExistsException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
