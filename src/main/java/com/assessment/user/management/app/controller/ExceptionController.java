package com.assessment.user.management.app.controller;

import com.assessment.user.management.app.exceptions.EmailAlreadyExistsException;
import com.assessment.user.management.app.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.FORBIDDEN;


@ControllerAdvice
public class ExceptionController{

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> userAlreadyExists(EmailAlreadyExistsException exception) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), FORBIDDEN);
        return new ResponseEntity<>(exceptionResponse, FORBIDDEN);
    }
}
