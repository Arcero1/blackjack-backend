package com.qa.blackjack.entities;

import com.qa.blackjack.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;

public class LeaderBoardEntry {

    private String name;

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public String getOwner() {
        return owner;
    }

    private int credits;
    private String owner;

    public LeaderBoardEntry(UserProfile profile, String owner) {
        this.name = profile.getName();
        this.credits = profile.getCredits();
        this.owner = owner;
    }


}
