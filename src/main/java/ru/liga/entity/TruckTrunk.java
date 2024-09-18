package ru.liga.entity;

import java.util.ArrayList;
import java.util.List;

public class TruckTrunk {
    public final int HEIGHT = 6;
    public final int WIDTH = 6;
    private final List<Box> boxes = new ArrayList<>();
    private final Integer[][] space = new Integer[HEIGHT][WIDTH];

    public Integer[][] getSpace() {
        return space;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public int getLatestVolume(){
        int volumeSpace = WIDTH*HEIGHT;
        int volumeBoxes = boxes.stream()
                .mapToInt(Box::getVolume)
                .sum();
        return volumeSpace - volumeBoxes;

    }
}
