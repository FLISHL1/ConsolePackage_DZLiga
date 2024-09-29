package ru.liga.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class JsonBoxWriter {
    private static final Logger log = LoggerFactory.getLogger(JsonBoxWriter.class);
    private final ObjectMapper objectMapper;
    private final String filePath;

    public JsonBoxWriter(ObjectMapper objectMapper,
                         @Value(value = "${util.json.box.file}") String jsonBoxFilePath) {
        this.objectMapper = objectMapper;
        filePath = jsonBoxFilePath;
    }

    /**
     *
     * Записывает в файл {@code JSON_TRUCKS_FILE_NAME} грузовики в json представлении
     *
     * @param boxes Список коробок
     */
    public void writeToFile(List<Box> boxes) {
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
            throw new RuntimeException(e);
        }
    }
}
