package ru.liga.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.liga.entity.Box;
import ru.liga.exception.WriteJsonException;
import ru.liga.util.Writer;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonBoxWriter implements Writer<List<Box>> {
    private static final Logger log = LoggerFactory.getLogger(JsonBoxWriter.class);
    private final ObjectMapper objectMapper;

    public JsonBoxWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    /**
     * Записывает в файл  грузовики в json представлении
     * @param filePath путь до файла куда нужно записать коробки
     * @param boxes Список коробок
     * @throws WriteJsonException Ошибка в записи json
     */
    public void write(String filePath, List<Box> boxes) {
        try {
            log.info("Start write file: {}", filePath);
            File fileJson = new File(filePath);
            if (fileJson.getParentFile().mkdir() && fileJson.createNewFile()) {
                log.info("Create new json file with boxes list in path: {}", filePath);
            } else {
                log.info("File with exist in path: {}", filePath);
            }
            objectMapper.writeValue(fileJson, boxes);
            log.info("End write file: {}", filePath);
        } catch (IOException e) {
            log.error("Error with write file: {}, exception: {}", filePath, e.getMessage());
            throw new WriteJsonException(e.getMessage());
        }
    }
}
