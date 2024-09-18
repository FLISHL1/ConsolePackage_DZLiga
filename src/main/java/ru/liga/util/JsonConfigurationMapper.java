package ru.liga.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConfigurationMapper {
    private final ObjectMapper objectMapper;

    public JsonConfigurationMapper(){
        objectMapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
