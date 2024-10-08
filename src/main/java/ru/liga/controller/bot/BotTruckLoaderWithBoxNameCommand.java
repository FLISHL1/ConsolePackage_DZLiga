package ru.liga.controller.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.liga.entity.Truck;
import ru.liga.mapper.Mapper;
import ru.liga.service.truck.FillTruckService;
import ru.liga.truckLoader.TruckLoader;

import java.util.Arrays;
import java.util.List;

@Controller
public class BotTruckLoaderWithBoxNameCommand {
    private static final Logger log = LoggerFactory.getLogger(BotTruckLoaderWithBoxNameCommand.class);
    private final FillTruckService fillTruckService;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;
    private final Mapper<String[], String> mapBoxName;
    private final Mapper<String[], String> mapTruckSize;

    public BotTruckLoaderWithBoxNameCommand(FillTruckService fillTruckService, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader, @Qualifier("boxNameMapper") Mapper<String[], String> mapBoxName, @Qualifier("truckSizeMapper") Mapper<String[], String> mapTruckSize) {
        this.fillTruckService = fillTruckService;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
        this.mapBoxName = mapBoxName;
        this.mapTruckSize = mapTruckSize;
    }

    /**
     * Возвращает сообщение с максимально плотно погруженными грузовиками
     *
     * @param boxNames    Строчка с именами коробок
     * @param trucksSizes Строчка с размерами грузовиков
     * @return Сообщение с загруженными грузовиками
     */
    public String maximalLoaderTruck(String boxNames, String trucksSizes) {
        return fillTruckWithBoxes(mapBoxName.map(boxNames), mapTruckSize.map(trucksSizes), maximalTruckLoader);
    }

    /**
     * Возвращает сообщение с эффективно погруженными грузовиками
     *
     * @param boxNames Строчка с именами коробок
     * @param trucksSizes Строчка с размерами грузовиков
     * @return Сообщение с загруженными грузовиками
     */
    public String uniformLoaderTruck(String boxNames, String trucksSizes) {
        return fillTruckWithBoxes(mapBoxName.map(boxNames), mapTruckSize.map(trucksSizes), uniformTruckLoader);
    }


    private String fillTruckWithBoxes(String[] boxNames, String[] truckSize, TruckLoader truckLoader) {
        log.debug("{}", Arrays.stream(boxNames).toList());
        log.debug("{}", Arrays.stream(truckSize).toList());
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByName(boxNames, truckSize, truckLoader);
        StringBuilder stringBuilder = new StringBuilder("Погруженные грузовики: \n");
        for (Truck truck : trucks) {
            stringBuilder.append(truck.toString()).append("\n\n");
        }
        return stringBuilder.toString();
    }



}
