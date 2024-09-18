package ru.liga.exception;

public class LoadingCapacityExceededException extends RuntimeException {
    public LoadingCapacityExceededException() {
    }

    public LoadingCapacityExceededException(String message) {
        super(message);
    }
}
