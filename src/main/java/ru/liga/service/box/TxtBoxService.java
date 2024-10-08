package ru.liga.service.box;

import org.springframework.stereotype.Service;
import ru.liga.entity.Box;
import ru.liga.util.Parser;

import java.util.List;

@Service
public class TxtBoxService {
    private final Parser<Box, String> txtBoxParser;
    private final Parser<Box, String> stringBoxParser;

    public TxtBoxService(Parser<Box, String> txtBoxParser, Parser<Box, String> stringBoxParser) {
        this.txtBoxParser = txtBoxParser;
        this.stringBoxParser = stringBoxParser;
    }

    /**
     * Получаем все коробки из файла
     *
     * @param fileName Название файла
     * @return Список коробок из файла
     */
    public List<Box> getAll(String fileName) {
        return txtBoxParser.parse(fileName);
    }

    public List<Box> getAllString(String file) {
        return stringBoxParser.parse(file);
    }
}
