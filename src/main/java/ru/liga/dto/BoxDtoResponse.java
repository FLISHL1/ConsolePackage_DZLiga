package ru.liga.dto;

import java.util.List;

public class BoxDtoResponse {
    private int id;
    private String name;
    private List<List<String>> space;

    public BoxDtoResponse(int id, String name, List<List<String>> space) {
        this.id = id;
        this.name = name;
        this.space = space;
    }

    public String getName() {
        return name;
    }

    public List<List<String>> getSpace() {
        return space;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (List<String> line : space) {
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }

    public int getId() {
        return id;
    }
}
