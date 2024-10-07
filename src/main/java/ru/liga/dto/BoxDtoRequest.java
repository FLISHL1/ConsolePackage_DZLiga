package ru.liga.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class BoxDtoRequest {
    private String name;
    private List<List<String>> space;
    private String charSpace;

    public BoxDtoRequest(String name, List<List<String>> space, String charSpace) {
        this.name = name;
        this.space = space;
        this.charSpace = charSpace;
    }

    public String getName() {
        return name;
    }

    public List<List<String>> getSpace() {
        return space;
    }

    public String getCharSpace() {
        return charSpace;
    }
}
