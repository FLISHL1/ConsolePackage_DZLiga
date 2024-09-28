package ru.liga.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.entity.Box;
import ru.liga.exceptions.BoxSizeException;

import java.util.Collections;
import java.util.List;

public class TxtParser {
    private static final Logger log = LoggerFactory.getLogger(TxtParser.class);

    /**
     *
     * @param fileName Путь до файла txt в папке resources
     * @return Список корбок
     */
    public List<Box> parseBoxFromFile(String fileName){
        try {
            List<String> readingLines = new TxtReader().read(fileName);
            return new BoxParser().parse(readingLines);
        } catch (BoxSizeException e){
            log.error("Box not fit into the specified machine dimensions");
            return Collections.emptyList();
        }
    }


}
