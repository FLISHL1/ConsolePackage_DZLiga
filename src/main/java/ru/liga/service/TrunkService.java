package ru.liga.service;

import org.springframework.stereotype.Service;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.entity.Trunk;
import ru.liga.exceptions.BoxDoesNotFitException;

import java.util.List;


@Service
public class TrunkService {
    /**
     * + y    +
     * + |    +
     * +\/    +
     * +      +
     * +      +
     * +x->   +
     * ++++++++
     *
     * @param box Ящик который мы хотим погрузить
     * @param x   Точка с которой начнется запись коробки в горизонтали
     * @param y   Точка с которой начнется запись коробки в вертикали
     */
    public void addBoxInTrunk(Truck truck, Box box, int x, int y) {
        if (!isSpaceAvailable(truck, box, x, y)){
            throw new BoxDoesNotFitException();
        }
        List<List<String>> boxSpace = box.getSpace();
        for (List<String> line : boxSpace) {
            int lineX = x;
            for (String column : line) {
                truck.getTrunk().getSpace()[y][lineX++] = column;
            }
            y++;
        }
        truck.getTrunk().getBoxes().add(box);
    }

    /**
     * Ищет свобоное пространсто в грузовике <p>
     * Если находит то вставляет туда коробку
     *
     * @param truck Грузовик куда мы помещаем коробку
     * @param box Коробка
     * @return Удалось ли поместить коробку
     */
    public boolean addBoxInTrunkWithFindPlace(Truck truck, Box box){
        Trunk trunk = truck.getTrunk();
        for (int y = 0; y < trunk.HEIGHT; y++){
            for (int x = 0; x < trunk.WIDTH; x++){
                if (isBoxWithInBounds(truck, box, x, y)){
                    if (isSpaceAvailable(truck, box, x, y)){
                        addBoxInTrunk(truck, box, x, y);
                        return true;
                    }
                } else {
                    break;
                }
            }
        }
        return false;
    }

    private boolean isSpaceAvailable(Truck truck, Box box, int x, int y) {
        for (int i = 0; i < box.getHeight(); i++) {
            for (int j = 0; j < box.getWidth(); j++) {
                if (truck.getTrunk().getSpace()[y + i][x + j] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isBoxWithInBounds(Truck truck, Box box, int x, int y) {
        return x + box.getWidth() <= truck.getTrunk().WIDTH && y + box.getHeight() <= truck.getTrunk().HEIGHT;
    }

    /**
     * Проверяет помещается ли коробка в оставшееся пространство грузовика
     *
     * @param box             Коробка которую мы проверяем
     * @param remainingSpaceY Показывает оставшееся место в грузовике по y
     * @param remainingSpaceX Показывает оставшееся место в грузовике по x
     * @return boolean
     */
    public boolean isNextBox(Box box, int remainingSpaceY, int remainingSpaceX) {
        return box.getVolume() <= (remainingSpaceY * remainingSpaceX)
                && box.getWidth() <= remainingSpaceX
                && box.getHeight() <= remainingSpaceY;
    }

}
