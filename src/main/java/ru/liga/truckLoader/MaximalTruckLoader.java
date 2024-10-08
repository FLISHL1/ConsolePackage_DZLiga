package ru.liga.truckLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.exception.LoadingCapacityExceededException;
import ru.liga.service.box.BoxService;
import ru.liga.service.TrunkService;

import java.util.ArrayList;
import java.util.List;

@Component
public class MaximalTruckLoader implements TruckLoader {
    private static final Logger log = LoggerFactory.getLogger(MaximalTruckLoader.class);
    private final TrunkService trunkService;
    private final BoxService boxService;

    public MaximalTruckLoader(TrunkService trunkService, BoxService boxService) {
        this.trunkService = trunkService;
        this.boxService = boxService;
    }


    /**
     * Грузит в грузовики коробки заполняя практически все свобоное место
     *
     * @param boxes Список коробок для погрузки
     * @param trucks Список грузовиков
     * @return Список погруженных грузовиков
     * @throws LoadingCapacityExceededException Для погрузки всех посылок не хватает заданого количества грузовиков
     */
    @Override
    public List<Truck> load(List<Box> boxes, List<Truck> trucks) {
        final int FIRST_INDEX = 0;
        List<Truck> filledTrukList = new ArrayList<>();
        List<Box> unusedBox = new ArrayList<>();
        log.debug("Starting to load trucks with {} boxes.", boxes.size());
        while (!boxes.isEmpty()) {
            if (trucks.isEmpty()) {
                throw new LoadingCapacityExceededException();
            }
            boxes = boxService.sortBoxes(boxes);
            Truck filledTruck = loadOneTruck(boxes, unusedBox, trucks.remove(FIRST_INDEX));
            filledTrukList.add(filledTruck);
            log.debug("Truck loaded with boxes: \n{}", filledTruck);
            boxes.addAll(unusedBox);
            unusedBox = new ArrayList<>();
        }
        log.debug("Finished loading trucks. Total trucks loaded: {}", filledTrukList.size());
        return filledTrukList;
    }

    private Truck loadOneTruck(List<Box> boxes, List<Box> unusedBox, Truck truck) {
        final int START_Y = 0;
        final int START_X = 0;
        final int FIRST_BOX_INDEX = 0;
        int fixedCurrentY = START_Y;

        log.debug("Loading a new truck.");

        while (!boxes.isEmpty()) {
            Box box = boxes.remove(FIRST_BOX_INDEX);
            log.debug("Trying to add box: \n{}\n to truck.", box);
            if (fixedCurrentY + box.getHeight() > truck.getTrunk().getHeight() || box.getWidth() > truck.getTrunk().getWidth()) {
                log.debug("Box: \n{}\n is too large for the truck. Adding to unused boxes.", box);
                unusedBox.add(box);
                continue;
            }

            trunkService.addBoxInTrunk(truck, box, START_X, fixedCurrentY);
            log.debug("Box: \n{}\n added to truck. Current truck state: \n{}", box, truck);

            int currentY = fixedCurrentY;
            fixedCurrentY += box.getHeight();

            int currentX = box.getWidth();
            while (currentY < fixedCurrentY) {
                int remainingSpaceY = truck.getTrunk().getHeight() - currentY;
                int remainingSpaceX = truck.getTrunk().getWidth() - currentX;

                log.debug("Remaining space in truck is up to height {} - Width: {}, Height: {}", fixedCurrentY, remainingSpaceX, remainingSpaceY);

                List<Box> nextBoxList = boxes.stream()
                        .filter(boxF -> trunkService.isNextBox(boxF, remainingSpaceY, remainingSpaceX))
                        .toList();
                if (nextBoxList.isEmpty()) {
                    log.debug("No more boxes fit in the remaining space. Moving to next truck.");
                    break;
                }

                Box nextBox = nextBoxList.get(FIRST_BOX_INDEX);
                trunkService.addBoxInTrunk(truck, nextBox, currentX, currentY);
                boxes.remove(nextBox);
                log.debug("Next box: \n{}\n added to truck. Current truck state: \n{}", nextBox, truck);

                if (remainingSpaceX > nextBox.getWidth()) {
                    currentX += nextBox.getWidth();
                } else {
                    currentY += nextBox.getHeight();
                }
            }
        }

        log.debug("Finished loading truck. Final truck state: \n{}", truck);
        return truck;
    }
}
