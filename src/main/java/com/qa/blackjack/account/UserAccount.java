package com.qa.blackjack.account;

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
    private Timestamp createdAt;
    private int gamesPlayed = 0;
    private int gamesWon = 0;
    private String alias = "";

    public UserAccount() {}
    public UserAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }
    UserAccount(String email, String password, String alias) {
        this.email = email;
        this.password = password;
        this.alias = alias;
    }

    void hasPlayed(boolean hasWon) {
        this.gamesPlayed++;
        this.gamesWon += hasWon ? 1 : 0;
    }

    boolean comparePassword(String password) {
        return password.equals(this.password);
    }

    // GETTERS AND SETTERS /////////////////////////////////////////////////////////////////////////////////////////////
    // must be public //////////////////////////////////////////////////////////////////////////////////////////////////
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public String getAlias() {
        return alias;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }
    public int getGamesWon() {
        return gamesWon;
    }
}
