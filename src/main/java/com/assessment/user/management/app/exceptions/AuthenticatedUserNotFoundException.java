package com.assessment.user.management.app.exceptions;

public class AuthenticatedUserNotFoundException extends RuntimeException {
    public AuthenticatedUserNotFoundException(String message) {
        super(message);
    }
}
