package ru.liga.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.liga.entity.Box;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class JsonBoxReader {
    private static final Logger log = LoggerFactory.getLogger(JsonBoxReader.class);
    private final ObjectMapper objectMapper;

    private final String filePath;


    public JsonBoxReader(ObjectMapper objectMapper,
                         @Value("${util.json.box.file}") String filePath) {
        this.objectMapper = objectMapper;
        this.filePath = filePath;
    }

    /**
     *
     * @return Список коробок
     */
    public List<Box> read(){
        try {
            log.info("Start file read: {}", filePath);
            List<Box> boxes = objectMapper.readValue(new File(filePath),
                    new TypeReference<List<Box>>() {});
            log.info("End file read: {}", filePath);
            return boxes;
        } catch (IOException e) {
            log.error("Error with read file: {}, exception: {}", filePath, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
