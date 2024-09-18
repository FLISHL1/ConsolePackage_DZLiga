package ru.liga.truckLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.exception.LoadingCapacityExceededException;
import ru.liga.service.BoxService;
import ru.liga.service.TruckService;

import java.util.ArrayList;
import java.util.List;

public class MaximalTruckLoader extends TruckLoader {
    private static final Logger log = LoggerFactory.getLogger(MaximalTruckLoader.class);
    private final int START_Y = 0;
    private final int START_X = 0;
    private final int FIRST_BOX_INDEX = 0;
    private final TruckService truckService = new TruckService();
    private final BoxService boxService = new BoxService();

    @Override
    public List<Truck> load(List<Box> boxes, Integer countTrucks) {
        List<Truck> truckList = new ArrayList<>();
        List<Box> unusedBox = new ArrayList<>();
        log.debug("Starting to load trucks with {} boxes.", boxes.size());
        while (!boxes.isEmpty()) {
            if (truckList.size() > countTrucks) {
                throw new LoadingCapacityExceededException();
            }
            boxes = boxService.sortBoxes(boxes);
            Truck newTruck = loadOneTruck(boxes, unusedBox);
            truckList.add(newTruck);
            log.debug("Truck loaded with boxes: \n{}", newTruck);
            boxes.addAll(unusedBox);
            unusedBox = new ArrayList<>();
        }
        log.debug("Finished loading trucks. Total trucks loaded: {}", truckList.size());
        return truckList;
    }

    private Truck loadOneTruck(List<Box> boxes, List<Box> unusedBox) {
        Truck truck = new Truck();
        int fixedCurrentY = START_Y;

        log.debug("Loading a new truck.");

        while (!boxes.isEmpty()) {
            Box box = boxes.remove(FIRST_BOX_INDEX);
            log.debug("Trying to add box: {} to truck.", box);
            if (fixedCurrentY + box.getHeight() > truck.getTrunk().HEIGHT || box.getWidth() > truck.getTrunk().WIDTH) {
                log.debug("Box: {} is too large for the truck. Adding to unused boxes.", box);
                unusedBox.add(box);
                continue;
            }

            truckService.addBoxInTrunk(truck, box, START_X, fixedCurrentY);
            log.debug("Box: {} added to truck. Current truck state: {}", box, truck);

            int currentY = fixedCurrentY;
            fixedCurrentY += box.getHeight();

            int currentX = box.getWidth();
            while (currentY < fixedCurrentY) {
                int remainingSpaceY = truck.getTrunk().HEIGHT - currentY;
                int remainingSpaceX = truck.getTrunk().WIDTH - currentX;

                log.debug("Remaining space in truck is up to height {} - Width: {}, Height: {}", fixedCurrentY, remainingSpaceX, remainingSpaceY);

                List<Box> nextBoxList = boxes.stream()
                        .filter(boxF -> truckService.isNextBox(boxF, remainingSpaceY, remainingSpaceX))
                        .toList();
                if (nextBoxList.isEmpty()) {
                    log.debug("No more boxes fit in the remaining space. Moving to next truck.");
                    break;
                }

                Box nextBox = nextBoxList.get(FIRST_BOX_INDEX);
                truckService.addBoxInTrunk(truck, nextBox, currentX, currentY);
                boxes.remove(nextBox);
                log.debug("Next box: {} added to truck. Current truck state: {}", nextBox, truck);

                if (remainingSpaceX > nextBox.getWidth()) {
                    currentX += nextBox.getWidth();
                } else {
                    currentY += nextBox.getHeight();
                }
            }
        }

        log.debug("Finished loading truck. Final truck state: {}", truck);
        return truck;
    }


}
