package ru.liga.exception;

public class BoxDoesNotFitException extends RuntimeException {
    public BoxDoesNotFitException() {
    }

    public BoxDoesNotFitException(String message) {
        super(message);
    }
}
