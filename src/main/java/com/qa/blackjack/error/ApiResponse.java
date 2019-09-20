package com.qa.blackjack.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {

    private HttpStatus status;
    private int error;
    private String message;

    public ApiResponse(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }

    public ResponseEntity<Object> send(Object content) {
        return new ResponseEntity<>(
                content,
                new HttpHeaders(),
                status
        );
    }
}
