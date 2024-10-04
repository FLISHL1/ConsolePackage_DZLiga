package ru.liga.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.liga.entity.Box;

import java.util.List;

@Component
public class TxtBoxParser {
    private static final Logger log = LoggerFactory.getLogger(TxtBoxParser.class);
    private final Reader<List<String>> txtReader;

    public TxtBoxParser(Reader<List<String>> txtReader) {
        this.txtReader = txtReader;
    }


    /**
     *
     * @param fileName Путь до файла txt в папке resources
     * @return Список корбок
     */
    public List<Box> parseBoxFromFile(String fileName){
        List<String> readingLines = txtReader.read(fileName);
        return new BoxParser().parse(readingLines);

    }


}
