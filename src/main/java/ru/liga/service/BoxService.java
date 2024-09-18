package ru.liga.service;

import ru.liga.entity.Box;

import java.util.List;
import java.util.stream.Collectors;

public class BoxService {
    public List<Box> sortBoxes(List<Box> boxList) {
        return boxList.stream()
                .sorted((box1, box2) -> Integer.compare(box2.getVolume(), box1.getVolume()))
                .collect(Collectors.toList());
    }
}
