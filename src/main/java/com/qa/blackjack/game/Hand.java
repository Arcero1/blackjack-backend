package com.qa.blackjack.game;
import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public void clearHand() {
        cards.clear();
    }

    public int getScore() {
        return cards.stream().mapToInt(Card::getValue).sum();
    }

    public long getNumAces() {
        return cards.stream().filter(Card::isAce).count();
    }

    public int getNumCards() {
        return cards.size();
    }
}
