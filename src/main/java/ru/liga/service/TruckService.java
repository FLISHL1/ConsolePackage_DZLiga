package ru.liga.service;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TruckService {
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

    /**
     * Считает во всех грузовиках количество коробок по типам формы
     *
     * @param trucks Список грузовиков
     * @return Словарь с количеством каждого типа коробок
     */
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
