package com.qa.blackjack.game;

import com.qa.blackjack.game.Card;
import com.qa.blackjack.game.Deck;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.InvalidNameException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DeckTest {
    private static Deck deck;

    @BeforeClass
    public static void getDeck() throws InvalidNameException {
        deck = new Deck();
    }

    @Test
    public void freshBuiltDeckHas_52_CardsWithValuesBetween_1_And_11() {
        for (int i = 0; i < 52; i++) {
            Card card = deck.getCard();
            System.out.println(card.getName());
            assertTrue(card.getValue() <= 11 && card.getValue() >= 1);
        }
        assertNull("deck is populated incorrectly",deck.getCard());
    }

    @Test
    public void shuffledDeckIsDifferentThanAFreshlyBuiltDeck() throws InvalidNameException {
        Deck[] deck = new Deck[2];
        deck[0] = new Deck();
        deck[1] = new Deck();

        deck[0].shuffle();
        int n = 0;

        for (int i = 0; i < 52; i++) {
            Card card[] = {deck[0].getCard(), deck[1].getCard()};
            if(card[0].getName() == card[1].getName()) {
                n++;
            }
        }
        System.out.println("Cards in same places : " + n);
        assertTrue("Shuffling deck is the same as the clean one", n < 52);
    }

    @Test
    public void twoShuffledDecksAreDifferentToEachOther() throws InvalidNameException {
        Deck[] deck = new Deck[2];
        deck[0] = new Deck();
        deck[1] = new Deck();
        deck[0].shuffle();
        deck[1].shuffle();
        int n = 0;

        for (int i = 0; i < 52; i++) {
            Card card[] = {deck[0].getCard(), deck[1].getCard()};
            if(card[0].getName() == card[1].getName()) {
                n++;
            }
        }
        System.out.println("Cards in same places : " + n);
        assertTrue("Shuffling yielded the same result both times", n < 52);
    }

    @Test
    public void testHit() throws InvalidNameException {
        Deck deck = new Deck();
        Card nextCard = deck.getCard();
        System.out.println(nextCard.getId());
    }

    // NOTE: for the last two tests, there is a probabilistically insignificant chance of n being 0 even if the
    // algorithm is correct. This is so improbable that it will be treated as a failure to save programming effort.
}
