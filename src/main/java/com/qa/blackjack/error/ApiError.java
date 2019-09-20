package com.qa.blackjack.error;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApiError {

    private HttpStatus status;
    private int error;
    private String message;

    public ApiError(HttpStatus status, String message, int error) {
        super();
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }

    public int getError() {
        return error;
    }
}