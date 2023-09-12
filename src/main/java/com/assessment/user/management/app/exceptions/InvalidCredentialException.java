package com.assessment.user.management.app.exceptions;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String message, InvalidCredentialException ex) {
        super(message);
    }
}
