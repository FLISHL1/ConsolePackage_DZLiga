package ru.liga.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.liga.entity.Box;
import ru.liga.exception.ReadJsonException;
import ru.liga.util.Reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonBoxReader implements Reader<List<Box>> {
    private static final Logger log = LoggerFactory.getLogger(JsonBoxReader.class);
    private final ObjectMapper objectMapper;



    public JsonBoxReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     *  Читает файл json, возвращая список коробок
     * @param filePath Путь до файла json с коробками
     * @return {@code List<Box>} - Список коробок
     * @throws ReadJsonException Ошибка в чтении файла json
     */
    public List<Box> read(String filePath){
        try {
            log.info("Start file read: {}", filePath);
            List<Box> boxes = objectMapper.readValue(new File(filePath),
                    new TypeReference<List<Box>>() {});
            log.info("End file read: {}", filePath);
            return boxes;
        } catch (IOException e) {
            log.error("Error with read file: {}, exception: {}", filePath, e.getMessage());
            throw new ReadJsonException(e.getMessage());
        }
    }
}
