package com.qa.blackjack.packet;

import com.qa.blackjack.util.ApiErrorMessage;
import com.qa.blackjack.util.ApiStatus;

public class ApiError extends ApiResponse {
    public ApiError(ApiErrorMessage message) {
        super(500, ApiStatus.FAILURE, message.toString());
    }
}