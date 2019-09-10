package com.qa.blackjack.util;

import java.util.*;

public class ConstantsUtil {
    public static final List<String> SUITS = new ArrayList<>(Arrays.asList(
            "Diamonds",
            "Clubs",
            "Hearts",
            "Spades"
    ));

    private static Map<String, Integer> cardValues = new HashMap<>() {{
        put( "2", 2);
        put("3", 3);
        put("4", 4);
        put("5", 5);
        put("6", 6);
        put("7", 7);
        put("8", 8);
        put("9", 9);
        put("10", 10);
        put("Jack", 10);
        put("Queen", 10);
        put("King", 10);
        put("Ace", 11);
    }};

    public static final Map<String, Integer> CARD_VALUES = Collections.unmodifiableMap(cardValues);
}
