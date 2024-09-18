import org.junit.jupiter.api.Test;
import ru.liga.entity.Box;
import ru.liga.util.BoxParser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoxParser {


    private BoxParser boxParser = new BoxParser();

    @Test
    public void testParse_singleBox() {
        // Входные данные для одной коробки
        List<String> boxesData = Arrays.asList(
                "8888",
                "8888",
                ""
        );

        // Ожидаемое состояние коробки
        List<Box> boxes = boxParser.parse(boxesData);

        assertEquals(1, boxes.size());

        // Проверяем размеры коробки
        Box box = boxes.get(0);
        assertEquals(2, box.getHeight());
        assertEquals(4, box.getWidth());
        assertEquals(8, box.getVolume());

        // Проверяем содержимое коробки
        List<List<Integer>> expectedSpace = Arrays.asList(
                Arrays.asList(8, 8, 8, 8),
                Arrays.asList(8, 8, 8, 8)
        );
        assertEquals(expectedSpace, box.getSpace());
    }

    @Test
    public void testParseBoxes_multiple() {
        // Входные данные для нескольких коробок
        List<String> boxesData = Arrays.asList(
                "666",
                "666",
                "",
                "999",
                "999",
                "999"
        );

        // Ожидаемое состояние коробок
        List<Box> boxes = boxParser.parse(boxesData);

        assertEquals(2, boxes.size());

        // Проверяем первую коробку
        Box box1 = boxes.get(0);
        List<List<Integer>> expectedSpace1 = Arrays.asList(
                Arrays.asList(6, 6, 6),
                Arrays.asList(6, 6, 6)
        );
        assertEquals(expectedSpace1, box1.getSpace());

        // Проверяем вторую коробку
        Box box2 = boxes.get(1);
        List<List<Integer>> expectedSpace2 = Arrays.asList(
                Arrays.asList(9, 9, 9),
                Arrays.asList(9, 9, 9),
                Arrays.asList(9, 9, 9)
        );
        assertEquals(expectedSpace2, box2.getSpace());
    }

    @Test
    public void testParse_emptyBoxData() {
        // Входные данные: пустой список строк
        List<String> boxesData = Arrays.asList();

        // Проверяем, что результат будет пустым
        List<Box> boxes = boxParser.parse(boxesData);
        assertTrue(boxes.isEmpty());
    }

    @Test
    public void testParse_onlyBlankLines() {
        // Входные данные: только пустые строки
        List<String> boxesData = Arrays.asList(
                "",
                "",
                ""
        );

        // Проверяем, что результат будет пустым
        List<Box> boxes = boxParser.parse(boxesData);
        assertTrue(boxes.isEmpty());
    }

    @Test
    public void testParse_invalidData_throwsException() {
        // Входные данные с некорректным символом
        List<String> boxesData = Arrays.asList(
                "12a",
                "111"
        );

        // Проверяем, что метод выбрасывает NumberFormatException
        assertThrows(NumberFormatException.class, () -> boxParser.parse(boxesData));
    }

}

