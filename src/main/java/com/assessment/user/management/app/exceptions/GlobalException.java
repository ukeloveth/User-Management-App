package com.assessment.user.management.app.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(UserNotFoundException exception, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorMessage, NOT_FOUND);
    }
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFound exception, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorMessage, NOT_FOUND);
    }

}

