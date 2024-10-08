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
import ru.liga.service.truck.TruckService;
import ru.liga.service.TrunkService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TestUniformTruckLoader {

    @Mock
    private TruckService truckService;

    @Mock
    private TrunkService trunkService;

    @Mock
    private BoxService boxService;

    @InjectMocks
    private UniformTruckLoader truckLoader;

    private List<Truck> trucks;
    private List<Box> boxes;

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
        when(truckService.sortTrucksByFreeVolume(any())).thenReturn(trucks);
        when(trunkService.addBoxInTrunkWithFindPlace(any(), any())).thenReturn(true);

        // Выполнение метода
        List<Truck> loadedTrucks = truckLoader.load(boxes, trucks);

        // Проверка что метод был вызван корректно
        verify(boxService).sortBoxes(boxes);
        verify(truckService, times(2)).sortTrucksByFreeVolume(trucks); // дважды, т.к. для каждого ящика
        verify(trunkService, times(2)).addBoxInTrunkWithFindPlace(any(Truck.class), any(Box.class));

        // Проверка результата
        assert loadedTrucks != null;
    }

    @Test
    void testLoadBoxDoesNotFitThrowsException() {
        // Подготовка моков
        when(boxService.sortBoxes(any())).thenReturn(boxes);
        when(truckService.sortTrucksByFreeVolume(any())).thenReturn(trucks);
        when(trunkService.addBoxInTrunkWithFindPlace(any(), any())).thenReturn(false);

        // Выполнение и проверка выброса исключения
        assertThrows(LoadingCapacityExceededException.class, () -> truckLoader.load(boxes, trucks));

        // Проверка, что сортировка была вызвана
        verify(boxService).sortBoxes(boxes);
        verify(truckService).sortTrucksByFreeVolume(trucks);
        verify(trunkService, times(2)).addBoxInTrunkWithFindPlace(any(Truck.class), any(Box.class));
    }
}
