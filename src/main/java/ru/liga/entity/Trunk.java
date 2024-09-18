package ru.liga.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Trunk {
    public final int HEIGHT;
    public final int WIDTH;
    private final List<Box> boxes;
    private final int[][] space;

    public Trunk() {
        HEIGHT = 6;
        WIDTH = 6;
        boxes = new ArrayList<>();
        space = new int[HEIGHT][WIDTH];
    }

    public int[][] getSpace() {
        return space;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    @JsonIgnore
    public int getLatestVolume(){
        int volumeSpace = WIDTH*HEIGHT;
        int volumeBoxes = boxes.stream()
                .mapToInt(Box::getVolume)
                .sum();
        return volumeSpace - volumeBoxes;

    }
}

