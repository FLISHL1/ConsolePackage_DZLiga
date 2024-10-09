package ru.liga.service.truck;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.entity.Trunk;
import ru.liga.mapper.BoxNameMapper;
import ru.liga.mapper.TruckSizeMapper;
import ru.liga.service.box.BoxService;
import ru.liga.service.box.MultiPartBoxService;
import ru.liga.service.box.TxtBoxService;
import ru.liga.truckLoader.TruckLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.shell.command.invocation.InvocableShellMethod.log;

@Service
public class FillTruckService {
    private final TxtBoxService txtBoxService;
    private final BoxService boxService;
    private final MultiPartBoxService multiPartBoxService;
    private final JsonTruckService jsonTruckService;
    private final BoxNameMapper mapBoxName;
    private final TruckSizeMapper mapTruckSize;

    public FillTruckService(TxtBoxService txtBoxService, BoxService boxService, MultiPartBoxService multiPartBoxService, JsonTruckService jsonTruckService, BoxNameMapper mapBoxName, TruckSizeMapper mapTruckSize) {
        this.txtBoxService = txtBoxService;
        this.boxService = boxService;
        this.multiPartBoxService = multiPartBoxService;
        this.jsonTruckService = jsonTruckService;
        this.mapBoxName = mapBoxName;
        this.mapTruckSize = mapTruckSize;
    }

    /**
     * Загружает коробки из файла в грузовики заданого размера и выбранным способом
     *
     * @param filePath    путь до файла посылок
     * @param trucksSize  Массив строк с размерами грузовиков
     * @param truckLoader Способ загрузки
     * @return Список загруженных грузовиков
     */
    public List<Truck> fillTrucksWithBoxesByFile(String filePath, String[] trucksSize, TruckLoader truckLoader) {
        List<Box> boxes = txtBoxService.getAll(filePath);
        return fillTrucks(trucksSize, truckLoader, boxes);
    }

    /**
     * Загружает коробки из файла в грузовики заданого размера и выбранным способом
     *
     * @param filePath    путь до файла посылок
     * @param trucksSize  Строчка с размерами грузовиков
     * @param truckLoader Способ загрузки
     * @return Список загруженных грузовиков
     */
    public List<Truck> fillTrucksWithBoxesByFile(String filePath, String trucksSize, TruckLoader truckLoader) {
        List<Box> boxes = txtBoxService.getAll(filePath);
        return fillTrucks(mapTruckSize.map(trucksSize), truckLoader, boxes);
    }

    /**
     * Загружает коробки из файла в грузовики заданого размера и выбранным способом
     *
     * @param file        Файл строчкой
     * @param trucksSize  Массив строк с размерами грузовиков
     * @param truckLoader Способ загрузки
     * @return Список загруженных грузовиков
     */
    public List<Truck> fillTrucksWithBoxesByFileString(String file, String trucksSize, TruckLoader truckLoader) {
        List<Box> boxes = txtBoxService.getAllString(file);
        return fillTrucks(mapTruckSize.map(trucksSize), truckLoader, boxes);
    }

    /**
     * Загружает коробки из базы данных по имени в грузовики заданого размера и выбранным способом
     *
     * @param names       Названия коробок
     * @param trucksSize  Массив строк с размерами грузовиков
     * @param truckLoader Способ загрузки
     * @return Список загруженных грузовиков
     */
    public List<Truck> fillTrucksWithBoxesByName(String[] names, String[] trucksSize, TruckLoader truckLoader) {
        List<Box> boxes = boxService.getByNames(names);
        return fillTrucks(trucksSize, truckLoader, boxes);
    }

    /**
     * Загружает коробки из базы данных по имени в грузовики заданого размера и выбранным способом
     *
     * @param names       Названия коробок
     * @param trucksSize  Массив строк с размерами грузовиков
     * @param truckLoader Способ загрузки
     * @return Список загруженных грузовиков
     */
    public List<Truck> fillTrucksWithBoxesByName(String names, String trucksSize, TruckLoader truckLoader) {
        List<Box> boxes = boxService.getByNames(mapBoxName.map(names));
        return fillTrucks(mapTruckSize.map(trucksSize), truckLoader, boxes);
    }

    /**
     * Загружает коробки из базы данных по имени в грузовики заданого размера и выбранным способом
     *
     * @param file        Файл multipart
     * @param trucksSize  Массив строк с размерами грузовиков
     * @param truckLoader Способ загрузки
     * @return Список загруженных грузовиков
     */
    public List<Truck> fillTrucksWithBoxesByFile(MultipartFile file, String trucksSize, TruckLoader truckLoader) {
        List<Box> boxes = multiPartBoxService.getAll(file);
        return fillTrucks(mapTruckSize.map(trucksSize), truckLoader, boxes);
    }

    private List<Truck> fillTrucks(String[] trucksSize, TruckLoader truckLoader, List<Box> boxes) {
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
