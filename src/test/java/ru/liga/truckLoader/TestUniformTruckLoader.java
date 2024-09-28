package ru.liga.truckLoader;

import org.junit.jupiter.api.Test;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.exceptions.LoadingCapacityExceededException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestUniformTruckLoader {
    UniformTruckLoader uniformTruckLoader = new UniformTruckLoader();

    @Test
    void testSuccessfulLoad() {
        // Given
        Box box1 = createBox(2, 2);
        Box box2 = createBox(1, 1);
        List<Box> boxes = Arrays.asList(box1, box2);
        List<Truck> result = uniformTruckLoader.load(boxes, 1);

        assertEquals(1, result.size(), "There should be one truck used.");
    }

    @Test
    void testExceedCapacity() {
        Box box1 = createBox(2, 2);
        Box box2 = createBox(3, 3);
        Box box3 = createBox(3, 3);
        Box box4 = createBox(3, 3);
        Box box5 = createBox(3, 3);
        List<Box> boxes = Arrays.asList(box1, box2, box3, box4, box5);
        assertThrows(LoadingCapacityExceededException.class, () -> {
            uniformTruckLoader.load(boxes, 1);
        });
    }

    @Test
    void testLoadWithNoBoxes() {
        List<Box> boxes = new ArrayList<>();

        List<Truck> result = uniformTruckLoader.load(boxes, 1);

        assertEquals(1, result.size(), "There should be one truck used.");
    }

    @Test
    void testLoadWithMultipleTrucks() {
        // Given
        Box box1 = createBox(2, 2);
        Box box2 = createBox(3, 3);
        List<Box> boxes = Arrays.asList(box1, box2);

        List<Truck> result = uniformTruckLoader.load(boxes, 2);

        assertEquals(2, result.size(), "Two trucks should be used.");
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

