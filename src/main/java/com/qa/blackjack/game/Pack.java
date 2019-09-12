package com.qa.blackjack.game;

/**
 * A Pack is a big deck
 */
public class Pack extends Deck {

    public Pack(int numDecks) {
        for(int i = 0; i < numDecks; i++) {
            cards.addAll(new Deck().getAllCards());
        }
    }
}
