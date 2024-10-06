package ru.liga.service;

import org.springframework.stereotype.Service;
import ru.liga.entity.Box;
import ru.liga.exception.BoxNotFoundException;
import ru.liga.exception.IdentityNameBoxException;
import ru.liga.repository.BoxRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoxService {
    private final BoxRepository boxRepository;

    public BoxService(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    /**
     * Сортирует список коробок по убыванию объема
     *
     * @param boxList Список коробок
     * @return Отсортированный по убыванию объема список
     */
    public List<Box> sortBoxes(List<Box> boxList) {
        return boxList.stream()
                .sorted((box1, box2) -> Integer.compare(box2.getVolume(), box1.getVolume()))
                .collect(Collectors.toList());
    }

    /**
     * Сменяет знак пространства коробки
     *
     * @param box       Коробка для изменения символа заполния
     * @param charSpace Новый символ заполнения коробки
     */
    public void changeCharSpace(Box box, String charSpace) {
        for (List<String> line : box.getSpace()) {
            line.replaceAll(ignored -> charSpace);
        }
    }

    /**
     * Получения всех коробок
     * @return Список коробок
     */
    public List<Box> getAll() {
        return boxRepository.findAll();
    }

    /**
     * Получает коробку по имения
     * @param name Имя коробки
     * @return Коробка
     */
    public Optional<Box> getByName(String name) {
        return Optional.ofNullable(boxRepository.findByName(name));
    }

    /**
     * Получает список всех коробок по именно
     * @param names Массив имен коробок
     * @return Список коробок
     */
    public List<Optional<Box>> getByNames(String[] names) {
        List<Optional<Box>> boxes = new ArrayList<>();
        for (String name : names) {
            boxes.add(getByName(name));
        }
        return boxes;
    }

    /**
     * Сохраняет короюку
     * @param box Коробка для сохранения
     */
    public Box save(Box box) {
        if (getByName(box.getName()).isPresent()) {
            throw new IdentityNameBoxException();
        }
        return boxRepository.save(box);
    }

    /**
     * Обновляет коробку
     * @param box Коробка для обновления
     */
    public void update(Box box) {
        boxRepository.update(box);
    }

    /**
     * Удаляет коробку
     * @param box Коробка для удаления
     */
    public void remove(Box box) {
        boxRepository.remove(box);
    }

    /**
     * Удаляет коробку по имени
     * @param name Имя коробки для удаления
     */
    public void remove(String name) {
        boxRepository.removeByName(name);
    }
}
