package com.qa.blackjack.entities;

import com.google.gson.JsonObject;
import com.qa.blackjack.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class UserProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;

    public String getName() {
        return name;
    }

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
