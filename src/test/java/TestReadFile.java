import org.junit.jupiter.api.Test;
import ru.liga.Box;

import javax.naming.directory.InvalidAttributesException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.liga.Main.readBoxesFromFile;

public class TestReadFile {

    @Test
    void testReadBoxesFromFile_validFile() throws FileNotFoundException, InvalidAttributesException {
        ArrayList<Box> boxes = readBoxesFromFile("src/test/resources/valid_boxes.txt");
        assertEquals(6, boxes.size()); // Предположим, что в файле 3 коробки
    }

}
