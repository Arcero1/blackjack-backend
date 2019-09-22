package com.qa.blackjack.game;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import javax.naming.InvalidNameException;

import static com.qa.blackjack.util.ConstantsUtil.CARD_VALUES;
import static com.qa.blackjack.util.ConstantsUtil.SUITS;
import static org.junit.Assert.*;

public class CardTest {
    private String testSuit = "Hearts";

    @Test(expected = InvalidNameException.class)
    public void testCardThrowsExceptionForInvalidSuit() throws InvalidNameException {
        String randomString;
        do {
            randomString = RandomStringUtils.randomAlphanumeric(0, 20);
        } while(SUITS.contains(randomString));

        new Card("King", randomString);
    }

    @Test(expected = InvalidNameException.class)
    public void testCardThrowsExceptionForInvalidName() throws InvalidNameException {
        String randomString;
        do {
            randomString = RandomStringUtils.randomAlphabetic(0, 20);
        } while(CARD_VALUES.containsKey(randomString));

        new Card(randomString, testSuit);
    }

    @Test
    public void testCardsHaveMappedValues() {
        CARD_VALUES.forEach((name, value) -> {
            try {
                assertEquals(value, (Integer) new Card(name, testSuit).getValue());
            } catch (InvalidNameException ignore) {}
        });
    }

    @Test
    public void testCardIdIsCorrect() throws InvalidNameException {
        assertEquals("10S", new Card("10", "Spades").toString());
        assertEquals("5D", new Card("5", "Diamonds").toString());
        assertEquals("2C", new Card("2", "Clubs").toString());
        assertEquals("9H", new Card("9", "Hearts").toString());
        assertEquals("KC", new Card("King", "Clubs").toString());
        assertEquals("QD", new Card("Queen", "Diamonds").toString());
        assertEquals("JH", new Card("Jack", "Hearts").toString());
        assertEquals("AS", new Card("Ace", "Spades").toString());
    }

    @Test
    public void testIsAceIdentifiesAcesCorrectly() throws InvalidNameException {
        assertFalse(new Card("King", testSuit).isAce());
        assertTrue(new Ace(testSuit).isAce());
    }
}
