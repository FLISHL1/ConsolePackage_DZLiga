import org.junit.jupiter.api.Test;
import ru.liga.Box;
import ru.liga.Truck;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.liga.Main.loadTruckWithBox;

public class TestPackage {
    @Test
    void testLoadTruckWithSingleBox() {
        ArrayList<Box> boxList = new ArrayList<>();
        boxList.add(new Box(createBoxSpace(1))); // 1x1 box

        List<Truck> truckList = loadTruckWithBox(boxList);

        assertEquals(1, truckList.size());
        assertNotNull(truckList.get(0).getCargoSpace()[5][0]); // Проверяем, что коробка помещена
    }

    @Test
    void testLoadTruckWithMultipleBoxes() {
        ArrayList<Box> boxList = new ArrayList<>();
        boxList.add(new Box(createBoxSpace(1))); // 1x1 box
        boxList.add(new Box(createBoxSpace(2))); // 1x2 box
        boxList.add(new Box(createBoxSpace(3))); // 1x3 box

        List<Truck> truckList = loadTruckWithBox(boxList);

        assertEquals(1, truckList.size());
        assertNotNull(truckList.get(0).getCargoSpace()[5][5]); // 1x1 box
        assertNotNull(truckList.get(0).getCargoSpace()[5][4]); // 1x2 box
        assertNotNull(truckList.get(0).getCargoSpace()[5][2]); // 1x3 box
    }
    @Test
    void testLoadTruckWithMultipleBoxes1() {
        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(new Box(createBoxSpace(2, 2))); // Добавляем коробку 2x2
        boxes.add(new Box(createBoxSpace(3, 3))); // Добавляем коробку 3x3

        List<Truck> trucks = loadTruckWithBox(boxes);
        assertEquals(1, trucks.size()); // Ожидаем, что все коробки поместятся в одну фуру
    }

    @Test
    void testLoadTruckWithInsufficientSpace() {
        ArrayList<Box> boxList = new ArrayList<>();
        boxList.add(new Box(createBoxSpace(6))); // 6x1 box
        boxList.add(new Box(createBoxSpace(6))); // 6x1 box

        List<Truck> truckList = loadTruckWithBox(boxList);

        assertEquals(2, truckList.size()); // Две фуры должны быть загружены
    }

    @Test
    void testLoadTruckWithNoBoxes() {
        ArrayList<Box> boxList = new ArrayList<>();

        List<Truck> truckList = loadTruckWithBox(boxList);

        assertEquals(0, truckList.size()); // Фур не должно быть
    }

    private ArrayList<ArrayList<Integer>> createBoxSpace(int size) {
        ArrayList<ArrayList<Integer>> space = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> line = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                line.add(i + 1); // Заполняем значением
            }
            space.add(line);
        }
        return space;
    }

    private ArrayList<ArrayList<Integer>> createBoxSpace(int length, int width) {
        ArrayList<ArrayList<Integer>> space = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            ArrayList<Integer> line = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                line.add(1); // Заполняем единицами
            }
            space.add(line);
        }
        return space;
    }
}
