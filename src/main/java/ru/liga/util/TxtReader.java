package ru.liga.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

@Component
public class TxtReader implements Reader<List<String>> {
    private static final Logger log = LoggerFactory.getLogger(TxtReader.class);

    /**
     *
     * @param fileName Путь до файла txt в папке resources
     * @return Список сторк читаемого файла
     */
    public List<String> read(String fileName) {
        try {
            log.info("Start file read: {}", fileName);
            List<String> allLines = Files.readAllLines(new File(getClass().getClassLoader().getResource(fileName).toURI()).toPath());
            log.info("End file read: {}", fileName);
            return allLines;
        } catch (Exception e) {
            log.error("Error with read file: {}, exception: {}", fileName, e.getMessage());
            return Collections.emptyList();
        }
    }
}
