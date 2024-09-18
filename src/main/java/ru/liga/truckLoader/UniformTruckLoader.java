package ru.liga.truckLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.exception.LoadingCapacityExceededException;
import ru.liga.service.BoxService;
import ru.liga.service.TruckService;
import ru.liga.service.TrunkService;

import java.util.ArrayList;
import java.util.List;

public class UniformTruckLoader extends TruckLoader {
    private static final Logger log = LoggerFactory.getLogger(UniformTruckLoader.class);
    private final TruckService truckService = new TruckService();
    private final TrunkService trunkService = new TrunkService();
    private final BoxService boxService = new BoxService();

    @Override
    public List<Truck> load(List<Box> boxes, Integer countTrucks) {
        List<Truck> trucks = new ArrayList<>();
        log.info("Starting the loading process. Number of boxes: {}, Number of trucks: {}", boxes.size(), countTrucks);
        for (int i = 0; i < countTrucks; i++) {
            trucks.add(new Truck());
            log.info("Truck #{} created.", i + 1);
        }


        boxes = boxService.sortBoxes(boxes);
        log.info("Boxes sorted by size.");

        for (Box box : boxes) {
            log.info("Attempting to load box with dimensions (Width: {}, Height: {})", box.getWidth(), box.getHeight());
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

        log.info("Loading process completed. Number of trucks used: {}", trucks.size());
        log.debug("Truck: \n");
        for (Truck truck: trucks){
            log.debug(truck.toString()+"\n");
        }
        return trucks;
    }

}
