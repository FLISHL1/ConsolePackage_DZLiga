package ru.liga.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.liga.entity.Box;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BoxParser {

    private static final Logger log = LoggerFactory.getLogger(BoxParser.class);

    private static List<String> convertLineToList(String line) {
        return Arrays.stream(line.strip().split(""))
                .collect(Collectors.toList());
    }

    /**
     * Парсит коробки из списка строк
     *
     * @param boxesData Список строчек в которых информация о коробках
     * @return Список коробок
     */
    public List<Box> parse(List<String> boxesData) {
        log.info("Start parse boxes data");
        List<Box> boxes = new ArrayList<>();
        List<List<String>> boxSpace = new ArrayList<>();
        int nameIndex = 0;
        for (String line : boxesData) {
            if (line.isBlank()) {
                addBoxInBoxList(boxSpace, boxes, nameIndex++);
                boxSpace = new ArrayList<>();
                continue;
            }
            log.debug("Parse row: {}", line);
            List<String> lineInt = convertLineToList(line);
            boxSpace.add(lineInt);
        }
        addBoxInBoxList(boxSpace, boxes, nameIndex++);
        log.info("End parse boxes data");
        return boxes;
    }

    private void addBoxInBoxList(List<List<String>> boxSpace, List<Box> boxList, Integer nameIndex) {
        if (!boxSpace.isEmpty()) {
            Box newBox = new Box("Box" + nameIndex, boxSpace);
            boxList.add(newBox);
            log.debug("Box added: \n{}", newBox);
        }
    }
}
