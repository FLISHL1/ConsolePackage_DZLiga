package ru.liga.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class BoxDtoRequest {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private List<List<String>> space;
    @NotNull
    @NotBlank
    private String charSpace;

    public BoxDtoRequest(String name, List<List<String>> space) {
        this.name = name;
        this.space = space;
    }

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
