package ru.liga.util;

import org.springframework.stereotype.Component;
import ru.liga.entity.Box;

import java.util.List;

@Component
public class StringBoxParser implements Parser<Box, String>{
    private final Reader<List<String>> txtReader;
    private final BoxParser boxParser;
    public StringBoxParser(Reader<List<String>> txtReader, BoxParser boxParser) {
        this.txtReader = txtReader;
        this.boxParser = boxParser;
    }


    /**
     * Парсит коробки из строчки
     *
     * @param file Файл txt строчкой
     * @return Список корбок
     */
    public List<Box> parse(String file){
        List<String> readingLines = txtReader.readByString(file);
        return boxParser.parse(readingLines);

    }


}
