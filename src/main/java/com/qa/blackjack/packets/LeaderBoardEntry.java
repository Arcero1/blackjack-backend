package com.qa.blackjack.packets;

import com.qa.blackjack.entities.UserProfile;

public class LeaderBoardEntry {
    private String name;
    private int credits;
    private String owner;

    public LeaderBoardEntry(UserProfile profile, String owner) {
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