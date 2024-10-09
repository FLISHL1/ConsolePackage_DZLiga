package ru.liga.controller.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.liga.entity.Truck;
import ru.liga.mapper.BoxNameMapper;
import ru.liga.mapper.TruckSizeMapper;
import ru.liga.service.truck.FillTruckService;
import ru.liga.truckLoader.TruckLoader;

import java.util.Arrays;
import java.util.List;

@Controller
public class BotTruckLoaderWithBoxNameController {
    private static final Logger log = LoggerFactory.getLogger(BotTruckLoaderWithBoxNameController.class);
    private final FillTruckService fillTruckService;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;

    public BotTruckLoaderWithBoxNameController(FillTruckService fillTruckService, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader) {
        this.fillTruckService = fillTruckService;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
    }

    /**
     * Возвращает сообщение с максимально плотно погруженными грузовиками
     *
     * @param boxNames    Строчка с именами коробок
     * @param trucksSizes Строчка с размерами грузовиков
     * @return Сообщение с загруженными грузовиками
     */
    public String maximalLoaderTruck(String boxNames, String trucksSizes) {
        return fillTruckWithBoxes(boxNames, trucksSizes, maximalTruckLoader);
    }

    /**
     * Возвращает сообщение с эффективно погруженными грузовиками
     *
     * @param boxNames Строчка с именами коробок
     * @param trucksSizes Строчка с размерами грузовиков
     * @return Сообщение с загруженными грузовиками
     */
    public String uniformLoaderTruck(String boxNames, String trucksSizes) {
        return fillTruckWithBoxes(boxNames, trucksSizes, uniformTruckLoader);
    }


    private String fillTruckWithBoxes(String boxNames, String truckSize, TruckLoader truckLoader) {
        log.debug("{}", boxNames);
        log.debug("{}", truckSize);
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByName(boxNames, truckSize, truckLoader);
        StringBuilder stringBuilder = new StringBuilder("Погруженные грузовики: \n");
        for (Truck truck : trucks) {
            stringBuilder.append(truck.toString()).append("\n\n");
        }
        return stringBuilder.toString();
    }



}
