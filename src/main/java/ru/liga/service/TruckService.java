package ru.liga.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.entity.Trunk;
import ru.liga.exception.BoxNotFoundException;
import ru.liga.truckLoader.TruckLoader;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class TruckService {
    private static final Logger log = LoggerFactory.getLogger(TruckService.class);
    private final JsonTruckService jsonTruckService;
    private final BoxService boxService;
    private final TxtBoxService txtBoxService;
    private final TrunkService trunkService;

    public TruckService(JsonTruckService jsonTruckService, BoxService boxService, TxtBoxService txtBoxService, TrunkService trunkService) {
        this.jsonTruckService = jsonTruckService;
        this.boxService = boxService;
        this.txtBoxService = txtBoxService;
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

    /**
     * @param filePath    путь до файла посылок
     * @param trucksSize  Массив строк с размерами грузовиков
     * @param truckLoader Способ загрузки
     * @return Список загруженных грузовиков
     */
    public List<Truck> fillTrucksWithBoxesByName(String filePath, String[] trucksSize, TruckLoader truckLoader) {
        List<Box> boxes = txtBoxService.getAll(filePath);
        List<Truck> trucksLoaded = getfilledTrucks(trucksSize, truckLoader, boxes);
        return trucksLoaded;
    }

    /**
     * @param boxesName   Список имен типов коробок
     * @param trucksSize  Массив строк с размерами грузовиков
     * @param truckLoader Способ загрузки
     * @return Список загруженных грузовиков
     * @throws BoxNotFoundException Не найдена коробка из списка имен
     */
    public List<Truck> fillTrucksWithBoxesByName(String[] boxesName, String[] trucksSize, TruckLoader truckLoader) {
        List<Box> boxes = boxService.getByNames(boxesName).stream()
                .map(obj -> obj.orElseThrow(BoxNotFoundException::new))
                .collect(Collectors.toList());
        List<Truck> trucksLoaded = getfilledTrucks(trucksSize, truckLoader, boxes);
        return trucksLoaded;
    }

    private List<Truck> getfilledTrucks(String[] trucksSize, TruckLoader truckLoader, List<Box> boxes) {
        List<Truck> trucks = createListTrucks(trucksSize);
        List<Truck> trucksLoaded = truckLoader.load(boxes, trucks);
        jsonTruckService.save(trucksLoaded);
        return trucksLoaded;
    }

    private List<Truck> createListTrucks(String[] truckSize) {
        List<Truck> trucks = new ArrayList<>();
        AtomicInteger iIndex = new AtomicInteger();
        final int INDEX_WIDTH = 0;
        final int INDEX_HEIGHT = 1;
        for (String size : truckSize) {
            List<Integer> sizeInt = Arrays.stream(size.split("x")).map(Integer::parseInt).toList();
            trucks.add(new Truck(new Trunk(sizeInt.get(INDEX_WIDTH), sizeInt.get(INDEX_HEIGHT))));
            log.info("Truck #{} created with size {}.", iIndex.getAndIncrement(), size);
        }
        return trucks;
    }
}
