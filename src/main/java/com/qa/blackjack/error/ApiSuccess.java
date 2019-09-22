package com.qa.blackjack.error;

import com.qa.blackjack.util.ApiStatus;

public class ApiSuccess extends ApiResponse {
    public ApiSuccess(String message) {
        super(200, ApiStatus.SUCCESS, message);
    }
    public ApiSuccess() {
        super(200, ApiStatus.SUCCESS, "");
    }
}
