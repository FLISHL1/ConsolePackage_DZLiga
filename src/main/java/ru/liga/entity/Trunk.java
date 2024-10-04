package ru.liga.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Trunk {
    public final int HEIGHT;
    public final int WIDTH;
    private final List<Box> boxes;
    private final String[][] space;


    public Trunk(int WIDTH, int HEIGHT) {
        this(HEIGHT, WIDTH, new ArrayList<>(), new String[HEIGHT][WIDTH]);
    }
    @JsonCreator
    public Trunk(@JsonProperty("HEIGHT") int HEIGHT,
                 @JsonProperty("WIDTH") int WIDTH,
                 @JsonProperty("boxes")
                 List<Box> boxes,
                 @JsonProperty("space")
                 String[][] space) {
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.boxes = boxes;
        this.space = space;
    }

    public String[][] getSpace() {
        return space;
    }

    public List<Box> getBoxes() {
        return boxes;
    }



}

