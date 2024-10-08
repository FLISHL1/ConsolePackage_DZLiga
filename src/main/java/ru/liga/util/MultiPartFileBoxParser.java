package ru.liga.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.entity.Box;

import java.util.List;

@Component
public class MultiPartFileBoxParser implements Parser<Box, MultipartFile>{
    private final Reader<List<String>> txtReader;
    private final BoxParser boxParser;
    public MultiPartFileBoxParser(Reader<List<String>> txtReader, BoxParser boxParser) {
        this.txtReader = txtReader;
        this.boxParser = boxParser;
    }

    /**
     * Парсит коробки из multipart
     *
     * @param file Файл txt multipart
     * @return Список корбок
     */
    public List<Box> parse(MultipartFile file){
        List<String> readingLines = txtReader.read(file);
        return boxParser.parse(readingLines);

    }


}
