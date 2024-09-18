package ru.liga.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.entity.Box;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BoxParser {

    private static final Logger log = LoggerFactory.getLogger(BoxParser.class);

    public List<Box> parse(List<String> boxesData) {
        log.info("Start parse boxes data");
        List<Box> boxes = new ArrayList<>();
        List<List<Integer>> boxSpace = new ArrayList<>();
        for (String line : boxesData) {
            if (line.isBlank()) {
                addBoxInBoxList(boxSpace, boxes);
                boxSpace = new ArrayList<>();
                continue;
            }
            log.debug("Parse row: {}", line);
            List<Integer> lineInt = convertLineToIntegerList(line);
            boxSpace.add(lineInt);
        }
        addBoxInBoxList(boxSpace, boxes);
        log.info("End parse boxes data");
        return boxes;
    }

    private static List<Integer> convertLineToIntegerList(String line) {
        return Arrays.stream(line.strip().split(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private void addBoxInBoxList(List<List<Integer>> boxSpace, List<Box> boxList) {
        if (!boxSpace.isEmpty()) {
            Box newBox = new Box(boxSpace);
            boxList.add(newBox);
            log.debug("Box added: {}", newBox);
        }
    }
}
