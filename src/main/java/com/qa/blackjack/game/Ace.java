package com.qa.blackjack.game;

import javax.naming.InvalidNameException;

public class Ace extends Card {
    public Ace (String suit) throws InvalidNameException {
        super("Ace", suit);
    }

    public int getValue(int handValue) {
        return (handValue + 11 <= 21) ? 11 : 1;
    }
}