package com.qa.blackjack.packet;

import com.qa.blackjack.profile.UserProfile;

public class PO_UserProfileLeaderBoardEntry {
    private String name;
    private int credits;
    private String owner;

    public PO_UserProfileLeaderBoardEntry(UserProfile profile, String owner) {
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