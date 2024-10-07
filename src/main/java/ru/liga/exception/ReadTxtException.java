package ru.liga.exception;

public class ReadTxtException extends RuntimeException {
    public ReadTxtException() {
    }

    public ReadTxtException(String message) {
        super(message);
    }
}
