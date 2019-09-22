package com.qa.blackjack.packet;

import com.qa.blackjack.util.ApiStatus;

public class ApiResponsePacket extends ApiResponse {
    public ApiResponsePacket(Object message) {
        super(201, ApiStatus.SUCCESS, message);
    }
}
