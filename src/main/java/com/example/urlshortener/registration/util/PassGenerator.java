package com.example.urlshortener.registration.util;

public class PassGenerator {

    public static String generateRandomPassword(int length) {
        String availableChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+-=[]{}|;':\",./<>?";

        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * availableChars.length());
            passwordBuilder.append(availableChars.charAt(index));
        }

        return passwordBuilder.toString();
    }
}
