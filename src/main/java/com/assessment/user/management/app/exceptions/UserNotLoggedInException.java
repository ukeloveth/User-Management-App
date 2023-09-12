package com.assessment.user.management.app.exceptions;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException(String s) {
        super(s);
    }
}
