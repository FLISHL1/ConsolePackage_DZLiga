package ru.liga.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;

import java.util.*;

@Service
public class TruckService {
    private static final Logger log = LoggerFactory.getLogger(TruckService.class);
    private final JsonTruckService jsonTruckService;
    private final TrunkService trunkService;

    public TruckService(JsonTruckService jsonTruckService, TrunkService trunkService) {
        this.jsonTruckService = jsonTruckService;
        this.trunkService = trunkService;
    }

    /**
     * Сортирует по убыванию процента занятого  места в грузовиках
     *
     * @param trucks Список грузовиков
     * @return Отсортированный по убыванию процента занятого  места в грузовиках
     */
    public List<Truck> sortTrucksByFreeVolume(List<Truck> trucks) {
        return trucks.stream()
                .sorted((truck2, truck1) ->
                        Integer.compare(trunkService.calculatePercentOccupiedVolume(truck1.getTrunk()),
                                trunkService.calculatePercentOccupiedVolume(truck2.getTrunk())
                        ))
                .toList();
    }


    private Map<Box, Integer> countBoxInTruck(Truck truck) {
        Map<Box, Integer> countTypeBox = new HashMap<>();
        for (Box box : truck.getTrunk().getBoxes()) {
            countTypeBox.put(box, countTypeBox.getOrDefault(box, 0) + 1);
        }
        return countTypeBox;
    }


    private Map<Truck, Map<Box, Integer>> countBoxInTrucks(List<Truck> trucks) {
        Map<Truck, Map<Box, Integer>> countTypeBox = new HashMap<>();
        for (Truck truck : trucks) {
            countTypeBox.put(truck, countBoxInTruck(truck));
        }
        return countTypeBox;
    }

    /**
     * Считает во всех грузовиках количество коробок по типам формы
     *
     * @param file файл с грузовиками
     * @return Словарь с количеством каждого типа коробок
     */
    public Map<Truck, Map<Box, Integer>> calcCountBoxInTruckFromJson(MultipartFile file) {
        List<Truck> trucks = jsonTruckService.getAll(file);
        return countBoxInTrucks(trucks);
    }

    /**
     * Считает во всех грузовиках количество коробок по типам формы
     *
     * @param filePath путь до файла с грузовиками
     * @return Словарь с количеством каждого типа коробок
     */
    public Map<Truck, Map<Box, Integer>> calcCountBoxInTruckFromJson(String filePath) {
        List<Truck> trucks = jsonTruckService.getAll(filePath);
        return countBoxInTrucks(trucks);
    }

}
