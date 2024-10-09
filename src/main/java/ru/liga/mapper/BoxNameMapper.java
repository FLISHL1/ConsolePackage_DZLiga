package ru.liga.mapper;

import org.springframework.stereotype.Component;

@Component
public class BoxNameMapper {

    public String[] map(String data) {
        return data.split("[ ,]");
    }
}
