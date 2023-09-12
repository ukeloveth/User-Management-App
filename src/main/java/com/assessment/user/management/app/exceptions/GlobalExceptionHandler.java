package com.assessment.user.management.app.exceptions;

import com.assessment.user.management.app.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> createHttpResponse (HttpStatus httpStatus, String message) {
        ErrorResponse errorDetails = new ErrorResponse(message, httpStatus.getReasonPhrase(), httpStatus.value(), httpStatus);
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<ErrorResponse> invalidCredentialException(InvalidCredentialException ex) {
        return createHttpResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> userAlreadyExistException(UserAlreadyExistException ex) {
        return createHttpResponse(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundException(UserNotFoundException ex) {
        return createHttpResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }


    @ExceptionHandler(AuthenticatedUserNotFoundException.class)
    public ResponseEntity<ErrorResponse> authenticatedUserNotFoundException(AuthenticatedUserNotFoundException ex) {
        return createHttpResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<ErrorResponse> userNotLoggedInException(UserNotLoggedInException ex) {
        return createHttpResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }



    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> taskNotFoundException(TaskNotFoundException ex) {
        return createHttpResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> invalidPasswordException(InvalidPasswordException ex) {
        return createHttpResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }


    @ExceptionHandler(TaskAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> taskAlreadyExistException(TaskAlreadyExistException ex) {
        return createHttpResponse(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
    }

}
