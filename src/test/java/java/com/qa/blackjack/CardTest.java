package java.com.qa.blackjack;

import com.qa.blackjack.game.Card;
import org.junit.Test;

import javax.naming.InvalidNameException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CardTest {
    @Test
    public void numberCardsHaveValuesCorrespondingToTheirNames() throws InvalidNameException {
        for(int i = 2; i < 11; i++) {
            int cardValue = i;
            assertEquals((int) cardValue, new Card(Integer.toString(cardValue)).getValue());
        }
    }

    @Test
    public void faceCardsHaveValueOf10() throws InvalidNameException {
        assertEquals(10, new Card("Jack").getValue());
        assertEquals(10, new Card("Queen").getValue());
        assertEquals(10, new Card("King").getValue());
    }

    @Test
    public void cardsHaveAnOptionalSuitAnd_toString_GivesBothValueAndSuit() throws InvalidNameException {
        assertEquals("King of Diamonds", new Card("King", "Diamonds").toString());
        assertEquals("King of Spades", new Card("King", "Spades").toString());
        assertEquals("King of Clubs", new Card("King", "Clubs").toString());
        assertEquals("King of Hearts", new Card("King", "Hearts").toString());
    }

    @Test
    public void valueCardIdIsCardValueAndFirstLetterOfSuit() throws InvalidNameException {
        assertEquals("10S", new Card("10", "Spades").getId());
        assertEquals("5D", new Card("5", "Diamonds").getId());
        assertEquals("2C", new Card("2", "Clubs").getId());
        assertEquals("9H", new Card("9", "Hearts").getId());
    }

    @Test
    public void faceCardIdIsFirstLetterOfNameAndFirstLetterOfSuit() throws InvalidNameException {
        assertEquals("KC", new Card("King", "Clubs").getId());
        assertEquals("QD", new Card("Queen", "Diamonds").getId());
        assertEquals("JH", new Card("Jack", "Hearts").getId());
        assertEquals("AS", new Card("Ace", "Spades").getId());
    }

    @Test (expected = InvalidNameException.class)
    public void incorrectCardNamesThrow_InvalidNameException_() throws InvalidNameException {
        new Card("Boy");
    }

    @Test (expected = InvalidNameException.class)
    public void incorrectCardSuitsThrow_InvalidNameException_() throws InvalidNameException {
        new Card("King", "yellow");
    }
}
