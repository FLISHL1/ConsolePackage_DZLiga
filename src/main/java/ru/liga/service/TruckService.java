package ru.liga.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.entity.Trunk;
import ru.liga.exception.BoxNotFoundException;
import ru.liga.truckLoader.TruckLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TruckService {
    private static final Logger log = LoggerFactory.getLogger(TruckService.class);
    private final JsonTruckService jsonTruckService;
    private final BoxService boxService;
    private final TxtBoxService txtBoxService;

    public TruckService(JsonTruckService jsonTruckService, BoxService boxService, TxtBoxService txtBoxService) {
        this.jsonTruckService = jsonTruckService;
        this.boxService = boxService;
        this.txtBoxService = txtBoxService;
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

    public List<Truck> fillTrucksWithBoxesByName(String filePath, Integer maxCountTruck, int width, int height, TruckLoader truckLoader) {
        List<Box> boxes = txtBoxService.getAll(filePath);
        List<Truck> trucksLoaded = truckLoader.load(boxes, maxCountTruck, width, height);
        jsonTruckService.save(trucksLoaded);
        return trucksLoaded;
    }

    public List<Truck> fillTrucksWithBoxesByName(String[] boxesData, Integer maxCountTruck, int width, int height, TruckLoader truckLoader) {
        List<Box> boxes = boxService.getByNames(boxesData).stream()
                .map(obj -> obj.orElseThrow(BoxNotFoundException::new))
                .collect(Collectors.toList());

        List<Truck> trucksLoaded = truckLoader.load(boxes, maxCountTruck, width, height);
        jsonTruckService.save(trucksLoaded);
        return trucksLoaded;
    }

    public List<Truck> createListTrucks(Integer countTrucks, int width, int height) {
        List<Truck> trucks = new ArrayList<>();
        for (int i = 0; i < countTrucks; i++) {
            trucks.add(new Truck(new Trunk(width, height)));
            log.info("Truck #{} created.", i + 1);
        }
        return trucks;
    }
}
