package ru.liga.service;

import org.springframework.stereotype.Service;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.truckLoader.TruckLoader;
import ru.liga.util.TxtBoxParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TruckService {
    private final JsonTruckService jsonTruckService;

    public TruckService(JsonTruckService jsonTruckService) {
        this.jsonTruckService = jsonTruckService;
    }

    /**
     * Сортирует по возрастанию свобоного места список грузовиков
     *
     * @param trucks Список грузовиков
     * @return Отсортированный по возрастанию свободного места список грузовиков
     */
    public List<Truck> sortTrucksByFreeVolume(List<Truck> trucks) {
        return trucks.stream()
                .sorted((truck2, truck1) -> Integer.compare(truck1.getTrunk().calculateLatestVolume(), truck2.getTrunk().calculateLatestVolume()))
                .toList();
    }


    private Map<Box, Integer> countBoxInTruck(Truck truck) {
        Map<Box, Integer> countTypeBox = new HashMap<>();
        for (Box box : truck.getTrunk().getBoxes()) {
            countTypeBox.put(box, countTypeBox.getOrDefault(box, 0) + 1);
        }
        return countTypeBox;
    }

    /**
     * Считает во всех грузовиках количество коробок по типам формы
     *
     * @param trucks Список грузовиков
     * @return Словарь с количеством каждого типа коробок
     */
    public Map<Truck, Map<Box, Integer>> countBoxInTrucks(List<Truck> trucks) {
        Map<Truck, Map<Box, Integer>> countTypeBox = new HashMap<>();
        for (Truck truck : trucks) {
            countTypeBox.put(truck, countBoxInTruck(truck));
        }
        return countTypeBox;
    }

    public List<Truck> fillTruckWithBoxes(String filePath, Integer maxCountTruck, TruckLoader truckLoader) {
        TxtBoxParser fileParser = new TxtBoxParser();
        List<Box> boxes = fileParser.parseBoxFromFile(filePath);
        List<Truck> trucksLoaded = truckLoader.load(boxes, maxCountTruck);
        jsonTruckService.writeFile(trucksLoaded);
        return trucksLoaded;
    }
}
