package com.qa.blackjack.game;


import com.qa.blackjack.util.ConstantsUtil;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

import static com.qa.blackjack.util.ConstantsUtil.CARD_VALUES;

public class Deck {
    List<Card> cards = new ArrayList<>();

    Deck() {
        ConstantsUtil.SUITS.forEach(suit -> CARD_VALUES.forEach((key, value) -> {
            try {
                cards.add(new Card(key, suit));
            } catch (InvalidNameException e) {
                e.printStackTrace();
            }
        }));
    }

    public void shuffle() {
        List<Card> shuffledCards = new ArrayList<>();
        while (cards.size() > 0) {
            shuffledCards.add(cards.remove((int) (Math.random() * cards.size())));
        }
        cards.addAll(shuffledCards);
    }

    public Card getCard() {
        try {
            return cards.remove(0);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Card> getAllCards() {
        return cards;
    }
}
