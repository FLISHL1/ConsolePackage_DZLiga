package ru.liga.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.entity.Truck;
import ru.liga.exception.ReadJsonException;
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
     * Получает из файла json список грузовиков
     *
     * @param fileName Путь до файла Json в папке проекта resources
     * @return Список грузовиков
     * @throws ReadJsonException Ошибка чтения json
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
            throw new ReadJsonException(e.getMessage());
        }
    }
    /**
     * Получает из файла json список грузовиков
     *
     * @param file multipart файл json
     * @return Список грузовиков
     * @throws ReadJsonException Ошибка чтения json
     */
    public List<Truck> read(MultipartFile file){
        try {
            log.info("Start multipartFile read: {}", file.getOriginalFilename());
            String json = new String(file.getBytes());
            List<Truck> trucks = objectMapper.readValue(json,
                    new TypeReference<List<Truck>>() {});
            log.info("End multipartFile read: {}", file.getOriginalFilename());
            return trucks;
        } catch (IOException e) {
            log.error("Error with read file: {}, exception: {}", file.getOriginalFilename(), e.getMessage());
            throw new ReadJsonException(e.getMessage());
        }
    }

    /**
     * Получает из файла json список грузовиков
     *
     * @param file файл строчкой json
     * @return Список грузовиков
     * @throws ReadJsonException Ошибка чтения json
     */
    public List<Truck> readByString(String file){
        try {
            log.info("Start file string read json");
            List<Truck> trucks = objectMapper.readValue(file,
                    new TypeReference<List<Truck>>() {});
            log.info("End file string read json");
            return trucks;
        } catch (IOException e) {
            log.error("Error with string read file json, exception: {}", e.getMessage());
            throw new ReadJsonException(e.getMessage());
        }
    }
}
