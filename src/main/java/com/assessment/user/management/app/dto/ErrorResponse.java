package com.assessment.user.management.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private Date timestamp;
    private String message;
    private String details;
    private int httpStatusCode;
    private HttpStatus httpStatus;

    public ErrorResponse(String message, String details, int httpStatusCode, HttpStatus httpStatus) {
        this.timestamp = new Date();
        this.message = message;
        this.details = details;
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
    }
}
