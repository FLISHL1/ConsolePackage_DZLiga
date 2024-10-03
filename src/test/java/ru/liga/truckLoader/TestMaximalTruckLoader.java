package ru.liga.truckLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.liga.Application;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.service.BoxService;
import ru.liga.service.TrunkService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestMaximalTruckLoader {
    @Mock
    private TrunkService trunkService = new TrunkService();

    @Mock
    private BoxService boxService;

    @InjectMocks
    private MaximalTruckLoader loader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoad_singleTruckAllBoxesFit() {
        // Создаем список коробок, которые все помещаются в один грузовик
        List<Box> boxes = Arrays.asList(
                createBox(3, 3),  // Box размером 2x2
                createBox(2, 2),  // Box размером 1x1
                createBox(1, 1)   // Box размером 3x3
        );
        when(boxService.sortBoxes(boxes)).thenReturn(boxes);


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
        List<List<String>> space = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<String> line = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                line.add(String.valueOf(i + 1)); // Заполняем значением
            }
            space.add(line);
        }
        return new Box(space);
    }

    private Box createBox(int length, int width) {
        List<List<String>> space = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            List<String> line = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                line.add("1"); // Заполняем единицами
            }
            space.add(line);
        }
        return new Box(space);
    }
}


