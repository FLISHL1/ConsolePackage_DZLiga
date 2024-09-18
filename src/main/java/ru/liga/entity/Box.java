package ru.liga.entity;

import java.util.*;

public class Box {
    private final int volume;
    private final int width;
    private final int height;

    private final List<List<Integer>> space;

    public Box(List<List<Integer>> space) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return width == box.width && height == box.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return "Box{" +
                "width=" + width +
                ", length=" + height +
                ", space=" + space +
                '}';
    }
}

