package com.qa.blackjack.entities;

import com.google.gson.JsonObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
public class UserAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
    @NotBlank private String email;
    @NotBlank private String password;
    private Timestamp created_at;
    private int games_played = 0;
    private int games_won = 0;
    private String alias = "";

    UserAccount() {}
    UserAccount(String email, String password) {
        this.email = email;
        this.password = password;
        this.alias = email.substring(0, email.indexOf("@"));
        this.created_at = new Timestamp(System.currentTimeMillis());
    }
    UserAccount(String email, String password, String alias) {
        this.email = email;
        this.password = password;
        this.alias = alias;
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

    public void hasPlayed(boolean hasWon) {
        this.games_played++;
        this.games_won += hasWon ? 1 : 0;
    }

    public boolean comparePassword(String password) {
        return password.equals(this.password);
    }

    public JsonObject toPublicJSON() {
        JsonObject json = new JsonObject();
        json.addProperty("alias", alias);
        json.addProperty("created_at", String.valueOf(this.created_at));
        json.addProperty("games_played", games_played);
        json.addProperty("games_won", games_won);

        return json;
    }

    // GETTERS AND SETTERS /////////////////////////////////////////////////////////////////////////////////////////////
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getEmail() {
        return email;
    }
    public int getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
}
