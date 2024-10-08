package ru.liga.controller.bot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.liga.entity.Truck;
import ru.liga.mapper.Mapper;
import ru.liga.service.truck.FillTruckService;
import ru.liga.truckLoader.TruckLoader;

import java.util.List;

@Controller
public class BotTruckLoaderFileCommand {
    private final FillTruckService fillTruckService;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;
    private final Mapper<String[], String> truckSizeMap;

    public BotTruckLoaderFileCommand(FillTruckService fillTruckService, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader, @Qualifier("truckSizeMapper") Mapper<String[], String> truckSizeMap) {
        this.fillTruckService = fillTruckService;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
        this.truckSizeMap = truckSizeMap;
    }

    /**
     * Возвращает сообщение с максимально плотно погруженными грузовиками
     *
     * @param file        Файл строчкой с коробками
     * @param trucksSizes Строчка с размерами грузовиков
     * @return Сообщение с загруженными грузовиками
     */
    public String maximalLoaderTruck(String file, String trucksSizes) {
        return fillTruckWithBoxes(file, truckSizeMap.map(trucksSizes), maximalTruckLoader);
    }

    /**
     * Возвращает сообщение с равномерно погруженными грузовиками
     *
     * @param file Файл строчкой с коробками
     * @param trucksSizes Строчка с размерами грузовиков
     * @return Сообщение с загруженными грузовиками
     */
    public String uniformLoaderTruck(String file, String trucksSizes) {
        return fillTruckWithBoxes(file, truckSizeMap.map(trucksSizes), uniformTruckLoader);
    }


    private String fillTruckWithBoxes(String file, String[] truckSize, TruckLoader truckLoader) {
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByFileString(file, truckSize, truckLoader);
        StringBuilder stringBuilder = new StringBuilder("Погруженные грузовики: \n");
        for (Truck truck : trucks) {
            stringBuilder.append(truck.toString()).append("\n");
        }
        return stringBuilder.toString();
    }


}
