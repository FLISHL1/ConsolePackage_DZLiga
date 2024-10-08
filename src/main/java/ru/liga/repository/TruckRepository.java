package ru.liga.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.entity.Truck;
import ru.liga.util.Reader;
import ru.liga.util.Writer;

import java.util.List;

@Repository
public class TruckRepository {
    private final Writer<List<Truck>> jsonWriter;
    private final Reader<List<Truck>> jsonReader;
    private final String filePath;

    public TruckRepository(Writer<List<Truck>> jsonWriter, Reader<List<Truck>> jsonReader, @Value(value = "${util.json.truck.write.file}") String filePath) {
        this.jsonWriter = jsonWriter;
        this.jsonReader = jsonReader;
        this.filePath = filePath;
    }

    /**
     * Записывает грузовики в файл {@code filePath}
     *
     * @param truckList Список грузовиков
     */
    public void write(List<Truck> truckList) {
        jsonWriter.write(filePath, truckList);
    }

    /**
     * Записывает грузовики в файл {@code filePath}
     * @param filePath Файл куда записать грузовики
     * @param truckList Список грузовиков
     */
    public void write(String filePath, List<Truck> truckList) {
        jsonWriter.write(filePath, truckList);
    }

    /**
     *
     * @param filePath Файл откуда читать грузовики
     * @return Спиок грузовиков
     */
    public List<Truck> read(String filePath) {
        return jsonReader.read(filePath);
    }

    public List<Truck> read() {
        return jsonReader.read(filePath);
    }
    public List<Truck> read(MultipartFile multipartFile) {
        return jsonReader.read(multipartFile);
    }

    public List<Truck> readByString(String file) {
        return jsonReader.readByString(file);
    }
}
