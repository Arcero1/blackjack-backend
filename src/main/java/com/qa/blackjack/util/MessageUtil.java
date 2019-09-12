package com.qa.blackjack.util;

public class MessageUtil {
    public static final String SUCCESS_GENERIC = "success";
    public static final String FAILURE_GENERIC = "failure";

    public static String msgItemNotFound(String item) {
        return FAILURE_GENERIC + ":[" + item + " NOT FOUND]";
    }
    public static String msgNotEnough(String item) {
        return FAILURE_GENERIC + ":[NOT ENOUGH " + item +"]";
    }
}
