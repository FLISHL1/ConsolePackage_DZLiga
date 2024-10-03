package ru.liga.truckLoader;

import ru.liga.entity.Box;
import ru.liga.entity.Truck;

import java.util.List;

public interface TruckLoader {
    List<Truck> load(List<Box> boxes, Integer countTrucks, int width, int height);

    List<Truck> load(List<Box> boxes, List<Truck> trucks);
}
