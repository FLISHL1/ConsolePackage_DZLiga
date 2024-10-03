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

    public List<Box> getAll(String fileName) {
        return txtBoxParser.parseBoxFromFile(fileName);
    }
}
