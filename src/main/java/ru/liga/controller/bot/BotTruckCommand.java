package ru.liga.controller.bot;

import org.springframework.stereotype.Controller;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.service.truck.TruckService;

import java.util.Map;

@Controller
public class BotTruckCommand {
    private final TruckService truckService;

    public BotTruckCommand(TruckService truckService) {
        this.truckService = truckService;
    }


    public String checkCountBoxInTrucks(
            String file
    ) {
        Map<Truck, Map<Box, Integer>> countBoxInTrucks = truckService.calcCountBoxInTruckFromJsonString(file);
        StringBuilder stringBuilder = new StringBuilder("Ниже будет указанно сколько и каких посылок было в грузовиках");
        for (Truck truck : countBoxInTrucks.keySet()) {
            stringBuilder.append("\n").append("---------------------------").append("\n");
            for (Box box : countBoxInTrucks.get(truck).keySet()) {
                stringBuilder.append(box.toString()).append(" - количество = ").append(countBoxInTrucks.get(truck).get(box)).append("\n");
            }
            stringBuilder.append("Вид загруженного грузовика: \n").append(truck);
        }
        return stringBuilder.toString();
    }


}
