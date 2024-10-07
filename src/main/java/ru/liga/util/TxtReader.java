package ru.liga.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<String> read(MultipartFile file) {
        try {
            log.info("Start multipart file read: {}", file.getOriginalFilename());
            List<String> allLines = Arrays.stream(new String(file.getBytes()).split("\n")).collect(Collectors.toList());
            log.info("End multipart file read: {}", file.getOriginalFilename());
            return allLines;
        } catch (Exception e) {
            log.error("Error with read file: {}, exception: {}", file.getOriginalFilename(), e.getMessage());
            return Collections.emptyList();
        }
    }
}
