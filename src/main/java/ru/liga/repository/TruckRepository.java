package ru.liga.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
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

    public void write(List<Truck> truckList) {
        jsonWriter.write(filePath, truckList);
    }

    public void write(String filePath, List<Truck> truckList) {
        jsonWriter.write(filePath, truckList);
    }

    public List<Truck> read(String filePath) {
        return jsonReader.read(filePath);
    }

    public List<Truck> read() {
        return jsonReader.read(filePath);
    }
}
