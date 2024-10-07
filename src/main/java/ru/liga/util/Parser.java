package ru.liga.util;

import java.util.List;

public interface Parser<T, V> {
    List<T> parse(V v);
}
