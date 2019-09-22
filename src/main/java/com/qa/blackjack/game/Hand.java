package com.qa.blackjack.game;
import java.util.ArrayList;
import java.util.List;

class Hand {
    List<Card> cards = new ArrayList<>();

    void addCard(Card card) {
        cards.add(card);
    }

    void clearHand() {
        cards.clear();
    }

    int getScore() {
        return cards.stream().mapToInt(Card::getValue).sum();
    }

    long getNumAces() {
        return cards.stream().filter(Card::isAce).count();
    }

    int getNumCards() {
        return cards.size();
    }
}
