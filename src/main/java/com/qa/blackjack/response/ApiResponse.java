package com.qa.blackjack.response;

public abstract class ApiResponse {

    private int code;
    private ApiStatus status;
    private Object message;

    public ApiResponse(int code, ApiStatus status, Object message) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public int getCode() {
        return this.code;
    }
    public ApiStatus getStatus() { return this.status; }
    public Object getMessage() {
        return this.message;
    }
}
