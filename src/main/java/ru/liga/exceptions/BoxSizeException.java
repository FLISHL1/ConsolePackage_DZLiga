package ru.liga.exceptions;

public class BoxSizeException extends RuntimeException {
    public BoxSizeException() {
    }

    public BoxSizeException(String message) {
        super(message);
    }
}
