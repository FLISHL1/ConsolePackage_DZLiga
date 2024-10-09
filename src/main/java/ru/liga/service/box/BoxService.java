package ru.liga.service.box;

import org.springframework.stereotype.Service;
import ru.liga.dto.BoxDtoRequest;
import ru.liga.dto.BoxDtoResponse;
import ru.liga.entity.Box;
import ru.liga.exception.BoxNotFoundException;
import ru.liga.exception.IdentityNameBoxException;
import ru.liga.mapper.DtoBoxMapper;
import ru.liga.repository.BoxRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoxService {
    private final BoxRepository boxRepository;
    private final DtoBoxMapper dtoBoxMapper;

    public BoxService(BoxRepository boxRepository, DtoBoxMapper dtoBoxMapper) {
        this.boxRepository = boxRepository;
        this.dtoBoxMapper = dtoBoxMapper;
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
     * Получения всех коробок
     * @return Список коробок
     */
    public List<BoxDtoResponse> getAll() {
        List<Box> box = boxRepository.findAll();
        return dtoBoxMapper.boxToDtoBoxResponse(box);
    }

    /**
     * Получает коробку по имения
     * @param name Имя коробки
     * @return Коробка
     * @throws BoxNotFoundException Не найдена коробка
     */
    public BoxDtoResponse getByName(String name) {
        Optional<Box> box = Optional.ofNullable(boxRepository.findByName(name));
        return dtoBoxMapper.boxToDtoBoxResponse(box.orElseThrow(BoxNotFoundException::new));
    }

    /**
     * Получает список всех коробок по именно
     * @param names Массив имен коробок
     * @return Список коробок
     * @throws BoxNotFoundException Не найдена коробка
     */
    public List<Box> getByNames(String[] names) {
        List<Box> boxes = new ArrayList<>();
        for (String name : names) {
            Box box = boxRepository.findByName(name);
            if (box == null) {
                throw new BoxNotFoundException();
            }
            boxes.add(box);
        }
        return boxes;
    }

    /**
     * Сохраняет короюку
     * @param boxDtoRequest Коробка для сохранения
     * @throws IdentityNameBoxException такая коробка уже есть
     */
    public BoxDtoResponse save(BoxDtoRequest boxDtoRequest) {
        Box box = dtoBoxMapper.dtoBoxRequestToBox(boxDtoRequest);
        if (boxRepository.findByName(box.getName()) == null) {
            throw new IdentityNameBoxException();
        }
        return dtoBoxMapper.boxToDtoBoxResponse(boxRepository.save(box));
    }

    /**
     * Обновляет коробку
     * @param boxDtoRequest Коробка для обновления
     * @param id Идентификатор коробки которую нужно обновить
     */
    public BoxDtoResponse update(Integer id, BoxDtoRequest boxDtoRequest) {
        Box box = dtoBoxMapper.dtoBoxRequestToBox(boxDtoRequest);
        box.setId(id);
        return dtoBoxMapper.boxToDtoBoxResponse(boxRepository.update(box));
    }

    /**
     * Обновляет коробку
     *
     * @param boxDtoRequest Коробка для обновления
     * @param name          Идентификатор коробки которую нужно обновить
     * @throws BoxNotFoundException Не найдена коробка
     */
    public BoxDtoResponse update(String name, BoxDtoRequest boxDtoRequest) {
        Box box = dtoBoxMapper.dtoBoxRequestToBox(boxDtoRequest);
        Box findIdBox = boxRepository.findByName(name);
        if (findIdBox == null) {
            throw new BoxNotFoundException();
        }
        box.setId(findIdBox.getId());
        return dtoBoxMapper.boxToDtoBoxResponse(boxRepository.update(box));
    }

    /**
     * Частично обновляет коробку
     * @param boxDtoRequest Коробка для обновления
     * @param id Идентификатор коробки которую нужно обновить
     */
    public BoxDtoResponse updatePart(Integer id, BoxDtoRequest boxDtoRequest) {
        Box boxByName = boxRepository.findById(id);
        if (boxByName == null) {
            throw new BoxNotFoundException();
        }
        Box box = dtoBoxMapper.dtoBoxRequestToBox(boxDtoRequest, boxByName);
        return dtoBoxMapper.boxToDtoBoxResponse(boxRepository.update(box));
    }

    /**
     * Обновляет коробку
     *
     * @param boxDtoRequest Коробка для обновления
     * @param name          Идентификатор коробки которую нужно обновить
     * @throws BoxNotFoundException Не найдена коробка
     */
    public BoxDtoResponse updatePart(String name, BoxDtoRequest boxDtoRequest) {
        Box boxByName = boxRepository.findByName(name);
        if (boxByName == null) {
            throw new BoxNotFoundException();
        }
        Box box = dtoBoxMapper.dtoBoxRequestToBox(boxDtoRequest, boxByName);
        return dtoBoxMapper.boxToDtoBoxResponse(boxRepository.update(box));
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
