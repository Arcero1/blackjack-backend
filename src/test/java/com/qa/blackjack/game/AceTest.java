package com.qa.blackjack.game;

import com.qa.blackjack.game.Ace;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.InvalidNameException;

import java.util.concurrent.atomic.AtomicInteger;

import static com.qa.blackjack.util.ConstantsUtil.SUITS;
import static org.junit.Assert.assertEquals;

public class AceTest {
    private static Ace ace;

    @BeforeClass
    public static void getAce() throws InvalidNameException {
        ace = new Ace("Diamonds");
    }

    @Test
    public void aceHasANativeValueOf11() {
        assertEquals(11, ace.getValue());
    }

    @Test
    public void aceHasValueOf_11_WithAnArgumentOf_10_OrLess() {
        for(int i = 0; i <= 10; i++) {
            assertEquals(11, ace.getValue(i));
        }
    }

    @Test
    public void aceHasValueOf_1_WithAnArgumentOf_11_OrMore() {
        for(int i = 11; i <= 21; i++) {
            assertEquals(1, ace.getValue(i));
        }
    }
}