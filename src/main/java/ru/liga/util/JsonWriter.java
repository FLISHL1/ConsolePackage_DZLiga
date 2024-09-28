package ru.liga.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.liga.entity.Truck;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonWriter {
    private static final Logger log = LoggerFactory.getLogger(JsonWriter.class);

    @Value(value = "${util.json.truck.write.file}")
    private final String JSON_TRUCKS_FILE_NAME = "json/trucks.json";

    /**
     *
     * Записывает в файл {@code JSON_TRUCKS_FILE_NAME} грузовики в json представлении
     *
     * @param trucks Список грузовиков
     */
    public void writeToFileTruckList(List<Truck> trucks) {
        ObjectMapper objectMapper = new JsonConfigurationMapper().getObjectMapper();
        try {
            log.info("Start write file: {}", JSON_TRUCKS_FILE_NAME);
            File fileJson = new File(JSON_TRUCKS_FILE_NAME);
            if (fileJson.getParentFile().mkdir() && fileJson.createNewFile()) {
                log.info("Create new json file with truck list in path: {}", JSON_TRUCKS_FILE_NAME);
            } else {
                log.info("File with exist in path: {}", JSON_TRUCKS_FILE_NAME);
            }
            objectMapper.writeValue(fileJson, trucks);
            log.info("End write file: {}", JSON_TRUCKS_FILE_NAME);
        } catch (IOException e) {
            log.error("Error with write file: {}, exception: {}", JSON_TRUCKS_FILE_NAME, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
