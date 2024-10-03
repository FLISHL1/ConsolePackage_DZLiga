package ru.liga.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.liga.entity.Truck;
import ru.liga.util.Writer;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonTruckWriter implements Writer<List<Truck>> {
    private static final Logger log = LoggerFactory.getLogger(JsonTruckWriter.class);
    private final ObjectMapper objectMapper;

    public JsonTruckWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     *
     * Записывает в файл {@code filePath} грузовики в json представлении
     *
     * @param trucks Список грузовиков
     */
    public void write(String filePath, List<Truck> trucks) {
        try {
            log.info("Start write file: {}", filePath);
            File fileJson = new File(filePath);
            if (fileJson.getParentFile().mkdir() && fileJson.createNewFile()) {
                log.info("Create new json file with truck list in path: {}", filePath);
            } else {
                log.info("File with exist in path: {}", filePath);
            }
            objectMapper.writeValue(fileJson, trucks);
            log.info("End write file: {}", filePath);
        } catch (IOException e) {
            log.error("Error with write file: {}, exception: {}", filePath, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
