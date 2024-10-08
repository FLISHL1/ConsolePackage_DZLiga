package ru.liga.mapper;

import org.springframework.stereotype.Component;

@Component
public class TruckSizeMapper implements Mapper<String[], String> {

    @Override
    public String[] map(String data) {
        return data.split("[ ,]");
    }
}
