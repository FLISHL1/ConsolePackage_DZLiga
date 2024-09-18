package ru.liga.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.entity.Truck;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class JsonReader {

    private static final Logger log = LoggerFactory.getLogger(JsonReader.class);

    public List<Truck> readTruckList(String fileName){
        ObjectMapper objectMapper = new JsonConfigurationMapper().getObjectMapper();
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
