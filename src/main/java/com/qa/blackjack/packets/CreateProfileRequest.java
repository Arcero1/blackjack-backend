package com.qa.blackjack.packets;

public class CreateProfileRequest {
    private String name;
    private String owner;

    CreateProfileRequest() {}
    CreateProfileRequest(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
