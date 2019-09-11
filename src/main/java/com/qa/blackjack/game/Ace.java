package com.qa.blackjack.game;

import javax.naming.InvalidNameException;

class Ace extends Card {
    Ace(String suit) throws InvalidNameException {
        super("Ace", suit);
    }

    int getValue(int handValue) {
        return (handValue + 11 <= 21) ? 11 : 1;
    }
}