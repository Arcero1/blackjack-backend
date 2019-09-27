package com.qa.blackjack.game;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.InvalidNameException;

import static org.junit.Assert.*;


public class HandTest {
    private static Hand hand;
    private static Card testCard;
    private static Card testCardAce;

    @BeforeClass
    public static void buildTestCards() throws InvalidNameException {
        hand = new Hand();
        testCard = new Card("King", "Hearts");
        testCardAce = new Ace("Hearts");
    }

    @Before
    public void clearCards() {
        hand.cards.clear();
    }

    @Test
    public void addTest() {
        hand.addCard(testCard);
        assertTrue(hand.cards.contains(testCard));

        hand.addCard(testCardAce);
        assertTrue(hand.cards.contains(testCard));
        assertTrue(hand.cards.contains(testCardAce));
    }

    @Test
    public void testClearHand() {
        hand.cards.add(testCard);
        assertFalse(hand.cards.isEmpty());

        hand.clearHand();
        assertTrue(hand.cards.isEmpty());
    }

    @Test
    public void testGetScore() {
        hand.cards.add(testCard);
        assertEquals(10, hand.getScore());

        hand.addCard(testCardAce);
        assertEquals(21, hand.getScore());
    }

    @Test
    public void testGetNumAces() {
        hand.cards.add(testCard);
        assertEquals(0, hand.getNumAces());

        hand.addCard(testCardAce);
        assertEquals(1, hand.getNumAces());
    }

    @Test
    public void testGetNumCards() {
        hand.cards.add(testCard);
        assertEquals(1, hand.getNumCards());

        hand.addCard(testCardAce);
        assertEquals(2, hand.getNumCards());
    }

}
