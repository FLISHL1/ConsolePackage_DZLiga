package ru.liga.exceptions;

public class BoxNotFoundException extends RuntimeException {
    public BoxNotFoundException() {
    }

    public BoxNotFoundException(String message) {
        super(message);
    }
}
