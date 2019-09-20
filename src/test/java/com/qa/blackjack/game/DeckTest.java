package com.qa.blackjack.game;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.InvalidNameException;

import static org.junit.Assert.*;

public class DeckTest {

    @Test
    public void testGetCardGivesACard() {
        assertNotNull(new Deck().getCard());
    }

    @Test
    public void freshBuiltDeckHas52CardsWithValuesBetween1And11() {
        Deck deck = new Deck();

        for (int i = 0; i < 52; i++) {
            Card card = deck.getCard();
            assertTrue(card.getValue() <= 11 && card.getValue() >= 1);
        }
        assertNull("deck is populated incorrectly",deck.getCard());
    }

    @Test
    public void shuffledDeckIsDifferentThanAFreshlyBuiltDeck() {
        Deck[] deck = new Deck[2];
        deck[0] = new Deck();
        deck[1] = new Deck();

        deck[0].shuffle();
        int n = 0;

        for (int i = 0; i < 52; i++) {
            Card[] card = {deck[0].getCard(), deck[1].getCard()};
            if(card[0].toString().equals(card[1].toString())) {
                n++;
            }
        }
        System.out.println("Cards in same places : " + n);
        assertTrue("Shuffling deck is the same as the clean one", n < 52);
    }

    @Test
    public void twoShuffledDecksAreDifferentToEachOther() {
        Deck[] deck = new Deck[2];
        deck[0] = new Deck();
        deck[1] = new Deck();
        deck[0].shuffle();
        deck[1].shuffle();
        int n = 0;

        for (int i = 0; i < 52; i++) {
            Card[] card = {deck[0].getCard(), deck[1].getCard()};
            if(card[0].toString().equals(card[1].toString())) {
                n++;
            }
        }
        System.out.println("Cards in same places : " + n);
        assertTrue("Shuffling yielded the same result both times", n < 52);
    }

    // NOTE: for the last two tests, there is a probabilistically insignificant chance of n being 0 even if the
    // algorithm is correct. This is so improbable that it will be treated as a failure to save programming effort.
}
