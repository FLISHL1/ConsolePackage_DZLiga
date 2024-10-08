package ru.liga.controller.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.liga.entity.Box;
import ru.liga.exception.BoxNotFoundException;
import ru.liga.exception.UserInputException;
import ru.liga.mapper.BoxMapper;
import ru.liga.service.box.BoxService;

import java.util.Arrays;
import java.util.List;

@Controller
public class BotBoxController {

    private static final Logger log = LoggerFactory.getLogger(BotBoxController.class);
    private final BoxService boxService;
    private final BoxMapper boxMapper;

    public BotBoxController(BoxService boxService, BoxMapper boxMapper) {
        this.boxService = boxService;
        this.boxMapper = boxMapper;
    }

    /**
     * Возвращает все коробки
     *
     * @return Строчка со всеми коробками
     */
    public String getAll() {
        List<Box> boxes = boxService.getAll();
        StringBuilder response = new StringBuilder("Все коробки:\n");
        for (Box box : boxes) {
            response.append(box.getName()).append("\n").append(box).append("\n");
        }
        return response.toString();
    }

    /**
     * Возвращает строчку с коробкой по id
     *
     * @param boxDetails Строчка с id коробки
     * @return Строчка с коробкой
     * @throws UserInputException Ошибка ввода пользователем
     */
    public String getByName(String boxDetails) {
        String[] details = mapDetails(boxDetails);
        if (details.length > 1) {
            throw new UserInputException();
        }
        log.debug("{}", Arrays.toString(details));
        Box box = boxService.getByName(details[0]).orElseThrow(BoxNotFoundException::new);
        return "Коробка: " + box.getName() + "\nРазмер: " + box.getSpace();
    }

    /**
     * Сохраняет коробку
     * @param boxDetails Строчка с данными коробки
     * @return Информация о том что коробка добавленна
     * @throws UserInputException Ошибка ввода пользователем
     */
    public String save(String boxDetails) {
        String[] details = mapDetails(boxDetails);
        if (details.length < 2) {
            throw new UserInputException();
        }
        String name = details[0].trim();
        String space = details[1].trim();

        Box box = new Box(name, boxMapper.mapSpaceStringToList(space));
        box = boxService.save(box);
        return "Коробка добавлена: " + box.getName();
    }

    private String[] mapDetails(String boxDetails) {
        return boxDetails.split(";");
    }

    /**
     * Обновляет полностью коробку
     * @param boxDetails вся информация по коробке
     * @return Сообщение что коробка обновлена
     * @throws UserInputException Ошибка ввода пользователем
     */
    public String update(String boxDetails) {
        String[] details = mapDetails(boxDetails);
        if (details.length < 2) {
            throw new UserInputException();
        }
        System.out.println(List.of(details));
        if (details.length < 4) {
            throw new UserInputException();
        }
        String name = details[0].trim();
        String newName = details[1].trim();
        String space = details[2].trim();
        String charSpace = details[3].trim();

        Box box = boxService.getByName(name).orElseThrow(BoxNotFoundException::new);
        box.setSpace(boxMapper.mapSpaceStringToList(space));
        box.setName(newName);
        boxService.changeCharSpace(box, charSpace);
        boxService.update(box);
        return "Коробка обновлена: " + box.getName();
    }

    /**
     * Удаляет коробку
     *
     * @param name Имя коробки
     * @return Сообщение что коробка удалена
     */
    public String delete(String name) {
        boxService.remove(name);
        return "Коробка удалена: " + name;
    }


}
