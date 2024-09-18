package ru.liga.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.entity.Truck;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonWriter {
    private static final Logger log = LoggerFactory.getLogger(JsonWriter.class);
    private final String JSON_TRUCKS_FILE_NAME = "json/trucks.json";

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
