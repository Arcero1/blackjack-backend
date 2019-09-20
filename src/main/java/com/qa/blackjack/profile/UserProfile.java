package com.qa.blackjack.profile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class UserProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
    @NotBlank private String name;
    private int uid = 1;
    private int credits = 300;

    public UserProfile() {
    }
    public UserProfile(String name) {
        this.name = name;
    }
    public UserProfile(String name, int uid) {
        this.name = name;
        this.uid = uid;
    }


    public String getName() {
        return name;
    }

    public void addCredits(int credits) {
        this.credits += credits;
    }

    // GETTERS AND SETTERS /////////////////////////////////////////////////////////////////////////////////////////////
    public int getOwnerId() {
        return uid;
    }
    public int getCredits() {
        return credits;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String creditsToString() {
        return "" + credits;
    }
}
