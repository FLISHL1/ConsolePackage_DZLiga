package ru.liga.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Trunk {
    private final int height;
    private final int width;
    private final List<Box> boxes;
    private final String[][] space;


    public Trunk(int width, int height) {
        this(height, width, new ArrayList<>(), new String[height][width]);
    }
    @JsonCreator
    public Trunk(@JsonProperty("HEIGHT") int height,
                 @JsonProperty("WIDTH") int width,
                 @JsonProperty("boxes")
                 List<Box> boxes,
                 @JsonProperty("space")
                 String[][] space) {
        this.height = height;
        this.width = width;
        this.boxes = boxes;
        this.space = space;
    }

    public String[][] getSpace() {
        return space;
    }

    public List<Box> getBoxes() {
        return boxes;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

