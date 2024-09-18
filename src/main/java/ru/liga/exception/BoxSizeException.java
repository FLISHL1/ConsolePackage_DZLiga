package ru.liga.exception;

public class BoxSizeException extends RuntimeException {
    public BoxSizeException() {
    }

    public BoxSizeException(String message) {
        super(message);
    }
}
