package ru.liga.service;

import org.springframework.stereotype.Service;
import ru.liga.entity.Box;
import ru.liga.util.TxtBoxParser;

import java.util.List;

@Service
public class TxtBoxService {
    private final TxtBoxParser txtBoxParser;

    public TxtBoxService(TxtBoxParser txtBoxParser) {
        this.txtBoxParser = txtBoxParser;
    }

    /**
     * Получаем все коробки из файла
     *
     * @param fileName Название файла
     * @return Список коробок из файла
     */
    public List<Box> getAll(String fileName) {
        return txtBoxParser.parseBoxFromFile(fileName);
    }
}
