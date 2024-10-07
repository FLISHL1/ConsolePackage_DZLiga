package ru.liga.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.entity.Trunk;
import ru.liga.exception.BoxNotFoundException;
import ru.liga.truckLoader.TruckLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.springframework.shell.command.invocation.InvocableShellMethod.log;

@Service
public class FillTruckService {
    private final TxtBoxService txtBoxService;
    private final BoxService boxService;
    private final MultiPartBoxService multiPartBoxService;
    private final JsonTruckService jsonTruckService;
    private final String REGEX_PLAIN = "[ ,]";

    public FillTruckService(TxtBoxService txtBoxService, BoxService boxService, MultiPartBoxService multiPartBoxService, JsonTruckService jsonTruckService) {
        this.txtBoxService = txtBoxService;
        this.boxService = boxService;
        this.multiPartBoxService = multiPartBoxService;
        this.jsonTruckService = jsonTruckService;
    }

    /**
     * @param filePath    путь до файла посылок
     * @param trucksSize  Массив строк с размерами грузовиков
     * @param truckLoader Способ загрузки
     * @return Список загруженных грузовиков
     */
    public List<Truck> fillTrucksWithBoxesByFile(String filePath, String[] trucksSize, TruckLoader truckLoader) {
        List<Box> boxes = txtBoxService.getAll(filePath);
        return fillTrucks(trucksSize, truckLoader, boxes);
    }

    public List<Truck> fillTrucksWithBoxesByName(String[] name, String[] trucksSize, TruckLoader truckLoader) {
        List<Box> boxes = boxService.getByNames(name).stream().map(opt -> opt.orElseThrow(BoxNotFoundException::new)).collect(Collectors.toList());
        return fillTrucks(trucksSize, truckLoader, boxes);
    }
    public List<Truck> fillTrucksWithBoxesByName(String name, String trucksSize, TruckLoader truckLoader) {
        String[] nameList = name.split(REGEX_PLAIN);
        List<Box> boxes = boxService.getByNames(nameList).stream().map(opt -> opt.orElseThrow(BoxNotFoundException::new)).collect(Collectors.toList());
        return fillTrucks(trucksSize, truckLoader, boxes);
    }


    public List<Truck> fillTrucksWithBoxesByFile(String filePath, String trucksSize, TruckLoader truckLoader) {
        List<Box> boxes = txtBoxService.getAll(filePath);
        return fillTrucks(trucksSize, truckLoader, boxes);
    }

    public List<Truck> fillTrucksWithBoxesByFile(MultipartFile file, String trucksSize, TruckLoader truckLoader) {
        List<Box> boxes = multiPartBoxService.getAll(file);
        return fillTrucks(trucksSize, truckLoader, boxes);
    }




    private List<Truck> fillTrucks(String[] trucksSize, TruckLoader truckLoader, List<Box> boxes) {
        List<Truck> trucks = createListTrucks(trucksSize);
        List<Truck> trucksLoaded = truckLoader.load(boxes, trucks);
        jsonTruckService.save(trucksLoaded);
        return trucksLoaded;
    }

    private List<Truck> fillTrucks(String trucksSize, TruckLoader truckLoader, List<Box> boxes) {
        String[] truckSizeList = trucksSize.split(REGEX_PLAIN);
        List<Truck> trucks = createListTrucks(truckSizeList);
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
