package ru.liga.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Box {
    @JsonIgnore
    private final int volume;
    @JsonIgnore
    private final int width;
    @JsonIgnore
    private final int height;

    private final List<List<Integer>> space;

    @JsonCreator
    public Box(@JsonProperty("space") List<List<Integer>> space) {
        this.space = space;
        height = space.size();
        width = space.stream()
                .max(Comparator.comparingInt(List::size))
                .orElse(new ArrayList<>())
                .size();
        volume = height * width;
    }

    public List<List<Integer>> getSpace() {
        return space;
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
        for (List<Integer> line: space){
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return Objects.equals(space, box.space);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(space);
    }
}

