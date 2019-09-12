package com.qa.blackjack.entities;

import com.google.gson.JsonObject;

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
    public UserProfile(String name, int uid) {
        this.name = name;
        this.uid = uid;
    }

    public void addCredits(int credits) {
        this.credits += credits;
    }

    public JsonObject toJSON() {
        JsonObject json = new JsonObject();
        json.addProperty("id", this.id);
        json.addProperty("name", this.name);
        json.addProperty("uid", this.uid);
        json.addProperty("credits", this.credits);

        return json;
    }

    // GETTERS AND SETTERS /////////////////////////////////////////////////////////////////////////////////////////////
    public int getOwnerId() {
        return uid;
    }
    public int getCredits() {
        return credits;
    }
    public String getCreditsAsString() {
        return "" + credits;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
}
