package com.qa.blackjack.game;

import javax.naming.InvalidNameException;
import static com.qa.blackjack.util.ConstantsUtil.CARD_VALUES;
import static com.qa.blackjack.util.ConstantsUtil.SUITS;

public class Card {
    private String name;
    private String suit;

    Card(String name, String suit) throws InvalidNameException {
        if(!CARD_VALUES.containsKey(name)) throw new InvalidNameException();
        if(!SUITS.contains(suit)) throw new InvalidNameException();
        this.suit = suit;
    }

    public String getId() {
        String id;

        try {
            Integer.parseInt(name);
            id = name;
        } catch (NumberFormatException e) {
            id = name.substring(0, 1);
        }

        return id + (suit.isEmpty() ? "" : suit.substring(0, 1));
    }

    public int getValue() {
        return CARD_VALUES.get(this.name);
    }
    String getName() {
        return this.name;
    }

    public String toString() {
        return this.name + (!this.suit.equals("") ? " of " + this.suit : "");
    }
}
