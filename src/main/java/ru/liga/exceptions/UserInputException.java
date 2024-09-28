package ru.liga.exceptions;

public class UserInputException extends RuntimeException {
    public UserInputException() {
    }

    public UserInputException(String message) {
        super(message);
    }
}
