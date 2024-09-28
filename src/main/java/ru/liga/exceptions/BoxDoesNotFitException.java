package ru.liga.exceptions;

public class BoxDoesNotFitException extends RuntimeException {
    public BoxDoesNotFitException() {
    }

    public BoxDoesNotFitException(String message) {
        super(message);
    }
}
