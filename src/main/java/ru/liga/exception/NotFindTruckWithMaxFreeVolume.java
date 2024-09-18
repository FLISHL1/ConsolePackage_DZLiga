package ru.liga.exception;

public class NotFindTruckWithMaxFreeVolume extends RuntimeException {
    public NotFindTruckWithMaxFreeVolume() {
    }

    public NotFindTruckWithMaxFreeVolume(String message) {
        super(message);
    }
}
