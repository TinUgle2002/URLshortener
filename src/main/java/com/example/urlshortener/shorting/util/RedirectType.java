package com.example.urlshortener.shorting.util;

public enum RedirectType {
    HTTP_301(301),
    HTTP_302(302);

    private final int value;

    RedirectType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static boolean checkValidRedirectType(int redirect) {

        if(redirect == HTTP_301.getValue() || redirect == HTTP_302.getValue()) {
            return true;
        }

        return false;
    }
}
