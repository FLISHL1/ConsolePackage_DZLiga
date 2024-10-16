package ru.liga.controller.bot;

import org.springframework.stereotype.Controller;
import ru.liga.entity.Truck;
import ru.liga.mapper.TruckSizeMapper;
import ru.liga.service.truck.FillTruckService;
import ru.liga.truckLoader.TruckLoader;

import java.util.List;

@Controller
public class BotTruckLoaderFileController {
    private final FillTruckService fillTruckService;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;

    public BotTruckLoaderFileController(FillTruckService fillTruckService, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader) {
        this.fillTruckService = fillTruckService;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
    }

    /**
     * Возвращает сообщение с максимально плотно погруженными грузовиками
     *
     * @param file        Файл строчкой с коробками
     * @param trucksSizes Строчка с размерами грузовиков
     * @return Сообщение с загруженными грузовиками
     */
    public String maximalLoaderTruck(String file, String trucksSizes) {
        return fillTruckWithBoxes(file, trucksSizes, maximalTruckLoader);
    }

    /**
     * Возвращает сообщение с равномерно погруженными грузовиками
     *
     * @param file Файл строчкой с коробками
     * @param trucksSizes Строчка с размерами грузовиков
     * @return Сообщение с загруженными грузовиками
     */
    public String uniformLoaderTruck(String file, String trucksSizes) {
        return fillTruckWithBoxes(file, trucksSizes, uniformTruckLoader);
    }


    private String fillTruckWithBoxes(String file, String truckSize, TruckLoader truckLoader) {
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByFileString(file, truckSize, truckLoader);
        StringBuilder stringBuilder = new StringBuilder("Погруженные грузовики: \n");
        for (Truck truck : trucks) {
            stringBuilder.append(truck.toString()).append("\n");
        }
        return stringBuilder.toString();
    }


}
