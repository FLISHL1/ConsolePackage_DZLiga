package ru.liga.util;

public interface Writer<T> {
    void write(String filePath, T t);
}
