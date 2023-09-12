package com.assessment.user.management.app.exceptions;


import jakarta.validation.constraints.NotBlank;

public class TaskAlreadyExistException extends RuntimeException {
    public TaskAlreadyExistException(@NotBlank(message = "Task is required") String s) {
        super(s);
    }
}
