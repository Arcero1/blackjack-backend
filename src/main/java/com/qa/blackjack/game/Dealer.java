package com.qa.blackjack.game;

class Dealer extends Player {
    Dealer(String difficulty) {
        super("Dealer");
    }

    void play(Deck deck) {
        deck.shuffle();
        System.out.println("Dealer is shuffling . . .");
        hit(deck);
        hit(deck);
        System.out.println("Dealer gets hand");
        while (getScore() < 15) {
            hit(deck);
            System.out.println("Dealer hits");
        }
        stand();
        System.out.println("Dealer stands");
    }


}
