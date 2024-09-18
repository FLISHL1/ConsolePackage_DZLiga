package ru.liga.service;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TruckService {

    public List<Truck> sortTrucksByFreeVolume(List<Truck> trucks) {
        return trucks.stream()
                .sorted((truck2, truck1) -> Integer.compare(truck1.getTrunk().getLatestVolume(), truck2.getTrunk().getLatestVolume()))
                .toList();
    }

    public Map<Box, Integer> countBoxInTrucks(List<Truck> trucks) {
        Map<Box, Integer> countTypeBox = new HashMap<>();
        for (Truck truck : trucks) {
            for (Box box : truck.getTrunk().getBoxes()){
                countTypeBox.put(box, countTypeBox.getOrDefault(box, 0)+1);
            }
        }
        return countTypeBox;
    }
}
