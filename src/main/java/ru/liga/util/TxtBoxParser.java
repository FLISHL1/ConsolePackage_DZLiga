package ru.liga.util;

import org.springframework.stereotype.Component;
import ru.liga.entity.Box;

import java.util.List;

@Component
public class TxtBoxParser implements Parser<Box, String>{
    private final Reader<List<String>> txtReader;
    private final BoxParser boxParser;
    public TxtBoxParser(Reader<List<String>> txtReader, BoxParser boxParser) {
        this.txtReader = txtReader;
        this.boxParser = boxParser;
    }


    /**
     *
     * @param fileName Путь до файла txt в папке resources
     * @return Список корбок
     */
    public List<Box> parse(String fileName){
        List<String> readingLines = txtReader.read(fileName);
        return boxParser.parse(readingLines);

    }


}
