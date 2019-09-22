package com.qa.blackjack.packet;

import com.qa.blackjack.util.ApiStatus;

public class ApiSuccess extends ApiResponse {

    public ApiSuccess(ApiStatus status, String message) {
        super(200, status, message);
    }
    public ApiSuccess(String message) {
        super(200, ApiStatus.SUCCESS, message);
    }
    public ApiSuccess() {
        super(200, ApiStatus.SUCCESS, "");
    }
}
