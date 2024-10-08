package ru.liga.truckLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.exception.LoadingCapacityExceededException;
import ru.liga.service.TrunkService;
import ru.liga.service.box.BoxService;
import ru.liga.service.truck.TruckService;

import java.util.List;

@Component
public class UniformTruckLoader implements TruckLoader {
    private static final Logger log = LoggerFactory.getLogger(UniformTruckLoader.class);
    private final TruckService truckService;
    private final TrunkService trunkService;
    private final BoxService boxService;

    public UniformTruckLoader(TruckService truckService, TrunkService trunkService, BoxService boxService) {
        this.truckService = truckService;
        this.trunkService = trunkService;
        this.boxService = boxService;
    }

    /**
     * Равномерно распределяет коробки по грузовикам <p>
     * Распределяет коробки по объему
     *
     * @param boxes Список коробок для погрузки
     * @param trucks Количество грузовиков по которым нужно распределить коробки
     * @return Список погруженных грузовиков
     * @throws LoadingCapacityExceededException Указывает на то что коробка не момещается ни в один из грузовиков
     */
    @Override
    public List<Truck> load(List<Box> boxes, List<Truck> trucks) {
        log.info("Starting the loading process. Number of boxes: {}, Number of trucks: {}", boxes.size(), trucks);
        boxes = boxService.sortBoxes(boxes);
        log.info("Boxes sorted by size.");
        for (Box box : boxes) {
            log.info("Attempting to load box with dimensions (Width: {}, Height: {})", box.getWidth(), box.getHeight());
            loadTruck(trucks, box);
        }
        log.info("Loading process completed. Number of trucks used: {}", trucks.size());
        log.debug("Truck: \n");
        logLoadedTrucks(trucks);
        return trucks;
    }

    private void logLoadedTrucks(List<Truck> trucks) {
        for (Truck truck: trucks){
            log.debug(truck.toString()+"\n");
        }
    }

    private void loadTruck(List<Truck> trucks, Box box) {
        boolean isPlace = false;
        trucks = truckService.sortTrucksByFreeVolume(trucks);
        log.info("Trucks sorted by free volume.");

        for (Truck truck : trucks) {
            if (trunkService.addBoxInTrunkWithFindPlace(truck, box)) {
                log.info("Box successfully loaded into truck #{}", trucks.indexOf(truck) + 1);
                isPlace = true;
                break;
            }
        }
        if (!isPlace) {
            log.error("Failed to load box with dimensions (Width: {}, Height: {}).", box.getWidth(), box.getHeight());
            throw new LoadingCapacityExceededException();
        }
    }


}
