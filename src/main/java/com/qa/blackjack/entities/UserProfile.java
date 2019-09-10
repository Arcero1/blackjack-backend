package com.qa.blackjack.entities;

import com.google.gson.JsonObject;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "name is necessary")
    private String name;
    private int uid = 1;

    public int getOwnerId() {
        return uid;
    }
    public int getCredits() {
        return credits;
    }

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

    @Override
    public String toString() {
        return "Name: " + name + ", Credits: " + credits + "\n";
    }

    public void addCredits(int credits) {
        this.credits += credits;
    }

    public UserProfile deleteUserAccountReference() {
        return this;
    }

    UserProfile get() {
    return this;
    }

    public JsonObject toJSON() {
        JsonObject json = new JsonObject();
        json.addProperty("id", this.id);
        json.addProperty("name", this.name);
        json.addProperty("uid", this.uid);
        json.addProperty("credits", this.credits);

        // json.add(); to get a nested object

        return json;
    }
}
