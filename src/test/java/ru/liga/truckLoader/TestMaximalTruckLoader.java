package ru.liga.truckLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.truckLoader.MaximalTruckLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMaximalTruckLoader {
    private MaximalTruckLoader loader;

    @Test
    public void testLoad_singleTruckAllBoxesFit() {
        // Создаем список коробок, которые все помещаются в один грузовик
        List<Box> boxes = Arrays.asList(
                createBox(3, 3),  // Box размером 2x2
                createBox(2, 2),  // Box размером 1x1
                createBox(1, 1)   // Box размером 3x3
        );


        // Выполняем загрузку
        List<Truck> truckList = loader.load(boxes, 1);

        // Проверяем, что все коробки уместились в один грузовик
        assertEquals(1, truckList.size());
    }

    @Test
    public void testLoad_multipleTrucksNeeded() {
        // Создаем список коробок, которые не помещаются в один грузовик
        List<Box> boxes = Arrays.asList(
                createBox(6, 6),  // Box, который полностью заполняет один грузовик
                createBox(2, 2)   // Box, который должен пойти во второй грузовик
        );


        // Выполняем загрузку
        List<Truck> truckList = loader.load(boxes, 2);

        // Проверяем, что требуется два грузовика
        assertEquals(2, truckList.size());
    }

    @Test
    public void testLoad_noBoxes() {
        // Пустой список коробок
        List<Box> boxes = Arrays.asList();

        // Выполняем загрузку
        List<Truck> truckList = loader.load(boxes, 1);

        // Проверяем, что грузовиков нет
        assertTrue(truckList.isEmpty());
    }
    private Box createBox(int size) {
        List<List<Integer>> space = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Integer> line = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                line.add(i + 1); // Заполняем значением
            }
            space.add(line);
        }
        return new Box(space);
    }

    private Box createBox(int length, int width) {
        List<List<Integer>> space = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            List<Integer> line = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                line.add(1); // Заполняем единицами
            }
            space.add(line);
        }
        return new Box(space);
    }
}


