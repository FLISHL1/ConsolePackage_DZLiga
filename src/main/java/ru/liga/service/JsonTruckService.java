package ru.liga.service;

import org.springframework.stereotype.Service;
import ru.liga.entity.Truck;
import ru.liga.util.JsonTruckReader;
import ru.liga.util.JsonTruckWriter;

import java.util.List;

@Service
public class JsonTruckService {
    private final JsonTruckWriter jsonWriter;
    private final JsonTruckReader jsonReader;

    public JsonTruckService(JsonTruckWriter jsonWriter, JsonTruckReader jsonReader) {
        this.jsonWriter = jsonWriter;
        this.jsonReader = jsonReader;
    }

    public void writeFile(List<Truck> truckList){
        jsonWriter.write(truckList);
    }

    public List<Truck> readJson(String filePath){
        return jsonReader.read(filePath);
    }
}
