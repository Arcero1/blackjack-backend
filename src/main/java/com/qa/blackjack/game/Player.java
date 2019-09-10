package com.qa.blackjack.game;

import java.util.ArrayList;
import java.util.List;

class Player {

    Player(String name) {
        this.name = name;
    }

    private List<Card> cards = new ArrayList<Card>();
    boolean finished = false;
    String name;
    private long money = 300;
    private long bet;

    Card hit(Deck deck) {
        cards.add(deck.getCard());
        return cards.get(cards.size() - 1);
    }

    String getCards() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Card card : cards) stringBuilder.append(" [").append(card.getName()).append("] ");

        return stringBuilder.toString();
    }

    int getScore() {
        int score = 0;
        for(Card card : cards) score += card.getValue();
        return score;
    }

    void stand() {
        this.finished = true;
    }

    void doubleBet() {
        this.bet *= 2;
    }

    long getMoney() {
        return this.money;
    }

    void newBet(long bet) {
        this.money -= bet;
        System.out.println("");
        this.bet = bet;
    }

    void surrender() {
        this.money += (this.bet / 2);
    }

    boolean isFinished() {
        return this.finished;
    }

    String getName() {
        return this.name;
    }
}
