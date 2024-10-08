package ru.liga.controller.shell;

import org.jline.terminal.Terminal;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.service.truck.TruckService;
import ru.liga.validator.FileNameValidator;

import java.util.Map;

@ShellComponent
@ShellCommandGroup("Truck-command")
public class ShellTruckCommand {
    private final Terminal terminal;
    private final FileNameValidator fileNameValidator;
    private final TruckService truckService;
    private final ShellFileValidator shellFileValidator;

    public ShellTruckCommand(Terminal terminal, FileNameValidator fileNameValidator, TruckService truckService, ShellFileValidator shellFileValidator) {
        this.terminal = terminal;
        this.fileNameValidator = fileNameValidator;
        this.truckService = truckService;
        this.shellFileValidator = shellFileValidator;
    }


    @ShellMethod(value = "Подсчет коробок в грузовиках", key = "count-box-in-trucks")
    private void checkCountBoxInTrucks(
            @ShellOption(help = "Путь до файла с посылками в формате .json для погрузка из папки resources", value = {"filePath", "-f"})
            String filePath
    ) {
        shellFileValidator.checkFileName(fileNameValidator.validateJson(filePath));
        Map<Truck, Map<Box, Integer>> countBoxInTrucks = truckService.calcCountBoxInTruckFromJson(filePath);
        terminal.writer().println("Ниже будет указанно сколько и каких посылок было в грузовиках");
        for (Truck truck : countBoxInTrucks.keySet()) {
            terminal.writer().println("---------------------------");
            for (Box box : countBoxInTrucks.get(truck).keySet()) {
                terminal.writer().println(box.toString() + " - количество = " + countBoxInTrucks.get(truck).get(box));
            }
            terminal.writer().println("Вид загруженного грузовика: \n" + truck);
        }
    }


}
