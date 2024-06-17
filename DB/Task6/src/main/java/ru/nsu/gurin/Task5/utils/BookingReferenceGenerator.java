package ru.nsu.gurin.Task5.utils;

import java.util.Random;

public class BookingReferenceGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 6;
    private static final Random RANDOM = new Random();

    public static String generateBookRef() {
        StringBuilder bookRef = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            bookRef.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return bookRef.toString();
    }
}