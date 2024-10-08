package ru.liga.mapper;

public interface Mapper<T, V> {
    T map(V v);
}
