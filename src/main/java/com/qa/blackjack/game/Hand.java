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
        int score = cards.stream().mapToInt(Card::getValue).sum();

        for(int i = 0; i < getNumAces(); i++) {
            if(score < 22) { break; }
            score -= 10;
        }
        return score;
    }

    long getNumAces() {
        return cards.stream().filter(Card::isAce).count();
    }

    int getNumCards() {
        return cards.size();
    }
}
