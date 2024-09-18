package ru.liga.exception;

public class UserInputException extends RuntimeException {
    public UserInputException() {
    }

    public UserInputException(String message) {
        super(message);
    }
}
