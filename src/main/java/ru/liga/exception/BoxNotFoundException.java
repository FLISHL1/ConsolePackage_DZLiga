package ru.liga.exception;

public class BoxNotFoundException extends RuntimeException {
    public BoxNotFoundException() {
        super("Коробка не найдена");
    }

    public BoxNotFoundException(String message) {
        super(message);
    }
}
