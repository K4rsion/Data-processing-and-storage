package ru.nsu.gurin.Task5.dto.exceptions;

public class NoSeatsAvailableException extends RuntimeException {
    public NoSeatsAvailableException(String message) {
        super(message);
    }
}
