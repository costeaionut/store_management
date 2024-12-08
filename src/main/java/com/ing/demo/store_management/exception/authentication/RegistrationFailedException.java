package com.ing.demo.store_management.exception.authentication;

public class RegistrationFailedException extends RuntimeException {
    public RegistrationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
