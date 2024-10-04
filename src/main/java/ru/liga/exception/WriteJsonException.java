package ru.liga.exception;

public class WriteJsonException extends RuntimeException {
    public WriteJsonException() {
    }

    public WriteJsonException(String message) {
        super(message);
    }
}
