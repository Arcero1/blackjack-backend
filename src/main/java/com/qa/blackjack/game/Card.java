package com.qa.blackjack.game;

import javax.naming.InvalidNameException;
import static com.qa.blackjack.util.ConstantsUtil.CARD_VALUES;
import static com.qa.blackjack.util.ConstantsUtil.SUITS;

public class Card {
    private String name;
    private String suit;

    Card(String name, String suit) throws InvalidNameException {
        if(!CARD_VALUES.containsKey(name) || !SUITS.contains(suit)) throw new InvalidNameException();

        this.name = name;
        this.suit = suit;
    }

    public String toString() {
        String id;

        try {
            Integer.parseInt(name);
            id = name;
        } catch (NumberFormatException e) {
            id = "" + name.charAt(0);
        }

        return id + (suit.isEmpty() ? "" : suit.charAt(0));
    }

    public int getValue() {
        return CARD_VALUES.get(this.name);
    }
    String getName() {
        return this.name;
    }
}
