package ru.liga.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.entity.Box;
import ru.liga.util.Parser;
import ru.liga.util.TxtBoxParser;

import java.util.List;

@Service
public class MultiPartBoxService {
    private final Parser<Box, MultipartFile> multipartFileParser;

    public MultiPartBoxService(Parser<Box, MultipartFile> multipartFileParser) {
        this.multipartFileParser = multipartFileParser;
    }

    /**
     * Получаем все коробки из файла
     *
     * @param file Файл
     * @return Список коробок из файла
     */
    public List<Box> getAll(MultipartFile file) {
        return multipartFileParser.parse(file);
    }
}
