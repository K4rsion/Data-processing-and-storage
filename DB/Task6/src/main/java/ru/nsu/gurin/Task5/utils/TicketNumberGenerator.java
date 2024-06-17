package ru.nsu.gurin.Task5.utils;

import java.util.Random;

public class TicketNumberGenerator {
    private static final int LENGTH = 13;
    private static final Random RANDOM = new Random();

    public static String generateTicketNumber() {
        StringBuilder ticketNumber = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            ticketNumber.append(RANDOM.nextInt(10)); // 0-9
        }
        return ticketNumber.toString();
    }
}