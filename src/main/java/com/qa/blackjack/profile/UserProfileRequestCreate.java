package com.qa.blackjack.profile;

public class UserProfileRequestCreate {
    private String name;
    private String owner;

    UserProfileRequestCreate() {}
    UserProfileRequestCreate(String name, String owner) {
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
