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

    public void changeCharSpace(Box box, String charSpace) {
        for (List<String> line : box.getSpace()) {
            line.replaceAll(ignored -> charSpace);
        }
    }

    public List<Box> getAll() {
        return boxRepository.findAll();
    }

    public Optional<Box> getByName(String name) {
        return Optional.ofNullable(boxRepository.findByName(name));
    }

    public List<Optional<Box>> getByNames(String[] names) {
        List<Optional<Box>> boxes = new ArrayList<>();
        for (String name : names) {
            boxes.add(getByName(name));
        }
        return boxes;
    }


    public void save(Box box) {
        if (getByName(box.getName()).isPresent()) {
            throw new IdentityNameBoxException();
        }
        boxRepository.save(box);
    }

    public void update(Box box) {
        boxRepository.update(box);
    }

    public void update(Box box, String oldName) {
        remove(oldName);
        boxRepository.save(box);
    }

    public void remove(Box box) {
        boxRepository.remove(box);
    }

    public void remove(String name) {
        Box box = boxRepository.findByName(name);
        if (box == null) {
            throw new BoxNotFoundException();
        }
        boxRepository.remove(box);
    }
}
