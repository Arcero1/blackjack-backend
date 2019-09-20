package com.qa.blackjack.account;

public class UserAccountPublicInfo {
    private int id;
    private String alias;
    private int gamesPlayed;
    private int gamesWon;

    public UserAccountPublicInfo(UserAccount user) {
        this.id = user.getId();
        this.alias = user.getAlias();
        this.gamesPlayed = user.getGamesPlayed();
        this.gamesWon = user.getGamesWon();
    }

    public int getId() {
        return id;
    }
    public String getAlias() {
        return alias;
    }
    public int getGamesPlayed() {
        return gamesPlayed;
    }
    public int getGamesWon() {
        return gamesWon;
    }
}
