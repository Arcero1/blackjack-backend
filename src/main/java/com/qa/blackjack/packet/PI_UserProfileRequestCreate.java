package com.qa.blackjack.packet;

public class PI_UserProfileRequestCreate {
    private String name;
    private String owner;

    public PI_UserProfileRequestCreate() {}
    public PI_UserProfileRequestCreate(String name, String owner) {
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
