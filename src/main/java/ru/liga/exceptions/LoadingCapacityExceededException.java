package ru.liga.exceptions;

public class LoadingCapacityExceededException extends RuntimeException {
    public LoadingCapacityExceededException() {
    }

    public LoadingCapacityExceededException(String message) {
        super(message);
    }
}
