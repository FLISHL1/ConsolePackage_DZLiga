package ru.liga.controller;

import org.jline.terminal.Terminal;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.entity.Box;
import ru.liga.exception.BoxNotFoundException;
import ru.liga.service.BoxService;

import java.util.List;
import java.util.Optional;

@ShellComponent
@ShellCommandGroup("Box-service")
public class ShellBoxController {
    private final BoxService boxService;
    private final Terminal terminal;

    public ShellBoxController(BoxService boxService, Terminal terminal) {
        this.boxService = boxService;
        this.terminal = terminal;
    }

    @ShellMethod(value = "Выводит все сохраненные типы коробок", key = {"box-all"})
    private void boxes() {
        List<Box> boxes = boxService.getAll();
        for (Box box : boxes) {
            southBox(box);
        }
    }

    @ShellMethod(value = "Выводит тип коробки", key = {"box"})
    private void boxByName(
            @ShellOption(help = "Имя типа коробки", value = {"name", "-n"})
            String name
    ) {
        southBox(boxService.getByName(name).orElseThrow(BoxNotFoundException::new));
    }

    @ShellMethod(value = "Сохраняет тип коробки", key = {"box-add"})
    private void add(
            @ShellOption(help = "Имя типа коробки", value = {"name", "-n"})
            String name,
            @ShellOption(help = """
                    Перечисление строк
                    Перечисление происходит через ','
                    Для перехода на новую строчку нужно разделить их пробелом. Например:
                    1,1,1 1,1,1
                    """, value = {"space", "-s"}, arity = Integer.MAX_VALUE - 1)
            List<List<String>> space
    ) {
        Box box = new Box(name, space);
        boxService.save(box);
        southBox(box);
    }

    @ShellMethod(value = "Обновляет тип коробки", key = {"box-update"})
    private void update(
            @ShellOption(help = "Имя типа коробки", value = {"name", "-n"})
            String name,
            @ShellOption(help = "Новое имя типа коробки", value = {"newName"}, defaultValue = ShellOption.NULL)
            String newNameArg,
            @ShellOption(help = """
                    Перечисление строк, на которые измениться изначальная форма
                    Перечисление происходит через ','
                    Для перехода на новую строчку нужно разделить их пробелом. Например:
                    1,1,1 1,1,1
                    """, value = {"space", "-s"}, arity = Integer.MAX_VALUE - 1, defaultValue = ShellOption.NULL)
            List<List<String>> spaceArg,
            @ShellOption(help = "Указывает новый символ формы, если не указан то будет использовться старый",
                    value = {"charSpace", "-c"}, defaultValue = ShellOption.NULL)
            String charSpaceArg
    ) {
        Optional<String> newNameOpt = Optional.ofNullable(newNameArg);
        Optional<List<List<String>>> spaceOpt = Optional.ofNullable(spaceArg);
        Optional<String> charSpaceOpt = Optional.ofNullable(charSpaceArg);

        Box box = boxService.getByName(name).orElseThrow(BoxNotFoundException::new);
        spaceOpt.ifPresent(box::setSpace);
        charSpaceOpt.ifPresent((ch) -> boxService.changeCharSpace(box, ch));
        newNameOpt.ifPresent(box::setName);
        boxService.update(box);
        southBox(box);
    }

    @ShellMethod(value = "Удаляет тип коробки", key = {"box-remove"})
    private void remove(
            @ShellOption(help = "Имя типа коробки", value = {"name", "-n"})
            String name
    ) {
        boxService.remove(name);
        terminal.writer().printf("Коробка %s удалена\n", name);
    }

    private void southBox(Box box) {
        StringBuilder boxString = new StringBuilder();
        boxString.append("Имя: ").append(box.getName()).append("\n");
        boxString.append("Форма: \n").append(box);
        terminal.writer().println(boxString);
    }
}
