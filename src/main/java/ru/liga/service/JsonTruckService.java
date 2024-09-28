package ru.liga.service;

import org.springframework.stereotype.Service;
import ru.liga.entity.Truck;
import ru.liga.util.JsonTruckReader;
import ru.liga.util.JsonWriter;

import java.util.List;

@Service
public class JsonTruckService {
    private final JsonWriter jsonWriter;
    private final JsonTruckReader jsonReader;

    public JsonTruckService(JsonWriter jsonWriter, JsonTruckReader jsonReader) {
        this.jsonWriter = jsonWriter;
        this.jsonReader = jsonReader;
    }

    public void writeFile(List<Truck> truckList){
        jsonWriter.writeToFileTruckList(truckList);
    }

    public List<Truck> readJson(String filePath){
        return jsonReader.readTruckList(filePath);
    }
}
