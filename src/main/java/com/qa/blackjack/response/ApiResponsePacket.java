package com.qa.blackjack.response;

public class ApiResponsePacket extends ApiResponse {
    public ApiResponsePacket(Object message) {
        super(201, ApiStatus.SUCCESS, message);
    }
}
