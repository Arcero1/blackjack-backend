package com.qa.blackjack.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PackTest {
    @Test
    public void checkPackHas_52xN_cards() {
        int N = (int) (Math.random() * 8) + 1;
        assertEquals(52 * N, new Pack(N).getAllCards().size());
    }
}
