package com.qa.blackjack.game;
import java.util.ArrayList;
import java.util.List;

class Hand {
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public void clearHand() {
        cards.clear();
    }

    public int getScore() {
        int score = cards.stream().mapToInt(Card::getValue).sum();

        for(int i = 0; i < getNumAces(); i++) {
            if(score < 22) { break; }
            score -= 10;
        }
        return score;
    }

    public long getNumAces() {
        return cards.stream().filter(Card::isAce).count();
    }

    public int getNumCards() {
        return cards.size();
    }
}
