package com.qa.blackjack.response;

public class ApiError extends ApiResponse {
    public ApiError(ApiErrorMessage message) {
        super(500, ApiStatus.FAILURE, message.toString());
    }
}