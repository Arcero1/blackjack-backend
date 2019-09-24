package com.qa.blackjack.profile;

public class UserProfileLeaderBoard {
    private String name;
    private int credits;
    private String owner;

    public UserProfileLeaderBoard(UserProfile profile, String owner) {
        this.name = profile.getName();
        this.credits = profile.getCredits();
        this.owner = owner;
    }

    public String getName() {
        return name;
    }
    public int getCredits() {
        return credits;
    }
    public String getOwner() {
        return owner;
    }
}