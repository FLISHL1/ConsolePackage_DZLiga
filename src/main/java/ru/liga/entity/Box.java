package ru.liga.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Box {
    @JsonIgnore
    private int volume;
    @JsonIgnore
    private int width;
    @JsonIgnore
    private int height;
    private List<List<String>> space;
    private String name;

    public Box(List<List<String>> space) {
        this(null, space);
    }

    @JsonCreator
    public Box(String name, List<List<String>> space) {
        this.name = name;
        setSpace(space);
        volume = height * width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List<String>> getSpace() {
        return space;
    }

    public void setSpace(List<List<String>> space) {
        this.space = space;
        height = space.size();
        width = space.stream()
                .max(Comparator.comparingInt(List::size))
                .orElse(new ArrayList<>())
                .size();
    }

    public int getVolume() {
        return volume;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (List<String> line : space) {
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return Objects.equals(name, box.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}

