package com.qa.blackjack.game;


import com.qa.blackjack.util.ConstantsUtil;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards = new ArrayList<>();

    public Deck() {
        rebuildDeck();
    }

    private void rebuildDeck() {
        cards.clear();

        ConstantsUtil.SUITS.forEach(suit -> {
            try {
                for (int i = 2; i < 11; i++) {
                    cards.add(new Card(String.valueOf(i), suit));
                }
                cards.add(new Card("Jack", suit));
                cards.add(new Card("Queen", suit));
                cards.add(new Card("King", suit));
                cards.add(new Ace(suit));

            } catch (InvalidNameException e) {
                e.printStackTrace();
            }
        });
    }

    public void shuffle() {
        List<Card> shuffledCards = new ArrayList<Card>();
        while (cards.size() > 0) {
            shuffledCards.add(cards.remove((int) (Math.random() * cards.size())));
        }
        cards.addAll(shuffledCards);
    }

    public Card getCard() {
        try {
            return cards.remove(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Deck is out of cards.");
        }
        return null;
    }
}
