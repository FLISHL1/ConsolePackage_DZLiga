package ru.liga.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.liga.entity.Truck;
import ru.liga.util.Reader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class JsonTruckReader implements Reader<List<Truck>> {
    private static final Logger log = LoggerFactory.getLogger(JsonTruckReader.class);
    private final ObjectMapper objectMapper;

    public JsonTruckReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     *
     * @param fileName Путь до файла Json в папке проекта resources
     * @return Список грузовиков
     */
    public List<Truck> read(String fileName){
        try {
            log.info("Start file read: {}", fileName);
            List<Truck> trucks = objectMapper.readValue(new File(getClass().getClassLoader().getResource(fileName).toURI()),
                    new TypeReference<List<Truck>>() {});
            log.info("End file read: {}", fileName);
            return trucks;
        } catch (URISyntaxException | IOException e) {
            log.error("Error with read file: {}, exception: {}", fileName, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
