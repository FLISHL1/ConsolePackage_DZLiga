package ru.liga.truckLoader;

import ru.liga.entity.Box;
import ru.liga.entity.Truck;

import java.util.List;

public abstract class TruckLoader {
    public abstract List<Truck> load(List<Box> boxes, Integer countTrucks);
}
