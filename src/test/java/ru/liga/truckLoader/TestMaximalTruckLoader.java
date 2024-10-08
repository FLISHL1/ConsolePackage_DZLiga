package ru.liga.truckLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.entity.Trunk;
import ru.liga.exception.LoadingCapacityExceededException;
import ru.liga.service.box.BoxService;
import ru.liga.service.TrunkService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TestMaximalTruckLoader {

    @Mock
    private TrunkService trunkService;

    @Mock
    private BoxService boxService;

    @InjectMocks
    private MaximalTruckLoader maximalTruckLoader;

    private List<Box> boxes;
    private List<Truck> trucks;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Инициализация тестовых данных
        Box box1 = mock(Box.class);
        when(box1.getHeight()).thenReturn(2);
        when(box1.getWidth()).thenReturn(2);
        when(box1.getVolume()).thenReturn(4);

        Box box2 = mock(Box.class);
        when(box2.getHeight()).thenReturn(3);
        when(box2.getWidth()).thenReturn(3);
        when(box2.getVolume()).thenReturn(9);

        Truck truck1 = mock(Truck.class);
        when(truck1.getTrunk()).thenReturn(new Trunk(6, 6));
        Truck truck2 = mock(Truck.class);
        when(truck2.getTrunk()).thenReturn(new Trunk(4, 5));

        boxes = new ArrayList<>();
        boxes.add(box2);
        boxes.add(box1);
        trucks = new ArrayList<>();
        trucks.add(truck1);
        trucks.add(truck2);
    }

    @Test
    void testLoadSuccessful() {
        // Подготовка моков
        when(boxService.sortBoxes(any())).thenReturn(boxes);
        when(trunkService.isNextBox(any(Box.class), anyInt(), anyInt())).thenReturn(true);
        doNothing().when(trunkService).addBoxInTrunk(any(Truck.class), any(Box.class), anyInt(), anyInt());

        // Выполнение метода
        List<Truck> loadedTrucks = maximalTruckLoader.load(boxes, trucks);

        // Проверка что метод был вызван корректно
        verify(trunkService, times(2)).addBoxInTrunk(any(Truck.class), any(Box.class), anyInt(), anyInt());

        // Проверка результата
        assert loadedTrucks != null;
        assert loadedTrucks.size() == 1; // два грузовика должны быть загружены
    }

    @Test
    void testLoadBoxDoesNotFitThrowsException() {
        // Подготовка моков
        when(boxService.sortBoxes(any())).thenReturn(boxes);

        // Выполнение метода и проверка выброса исключения
        assertThrows(LoadingCapacityExceededException.class, () -> maximalTruckLoader.load(boxes, new ArrayList<>()));

    }


}
