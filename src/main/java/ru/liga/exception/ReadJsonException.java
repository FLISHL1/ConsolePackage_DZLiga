package ru.liga.exception;

public class ReadJsonException extends RuntimeException {
    public ReadJsonException() {
    }

    public ReadJsonException(String message) {
        super(message);
    }
}
