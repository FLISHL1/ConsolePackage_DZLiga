package ru.liga.service;

import ru.liga.entity.Box;

import java.util.List;
import java.util.stream.Collectors;

public class BoxService {
    /**
     * Сортирует список коробок по убыванию объема
     *
     * @param boxList Список коробок
     * @return Отсортированный по убыванию объема список
     */
    public List<Box> sortBoxes(List<Box> boxList) {
        return boxList.stream()
                .sorted((box1, box2) -> Integer.compare(box2.getVolume(), box1.getVolume()))
                .collect(Collectors.toList());
    }
}
