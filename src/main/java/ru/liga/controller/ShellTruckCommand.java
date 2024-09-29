package ru.liga.controller;

import org.jline.terminal.Terminal;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.service.JsonTruckService;
import ru.liga.service.TruckService;
import ru.liga.validator.FileNameValidator;

import java.util.List;
import java.util.Map;

@ShellComponent
@ShellCommandGroup("Truck-command")
public class ShellTruckCommand {
    private final Terminal terminal;
    private final FileNameValidator fileNameValidator;
    private final TruckService truckService;
    private final JsonTruckService jsonTruckService;
    private final ShellFileValidator shellFileValidator;

    public ShellTruckCommand(Terminal terminal, FileNameValidator fileNameValidator, TruckService truckService, JsonTruckService jsonTruckService, ShellFileValidator shellFileValidator) {
        this.terminal = terminal;
        this.fileNameValidator = fileNameValidator;
        this.truckService = truckService;
        this.jsonTruckService = jsonTruckService;
        this.shellFileValidator = shellFileValidator;
    }


    @ShellMethod(value = "Загрузка коробок в грузовик из файла равномерно", key = "count-box-in-trucks")
    private void checkCountBoxInTrucks(
            @ShellOption(help = "Путь до файла с посылками в формате .json для погрузка из папки resources", value = {"filePath", "-f"})
            String filePath
    ) {
        shellFileValidator.checkFileName(fileNameValidator.validateJson(filePath));
        Map<Truck, Map<Box, Integer>> countBoxInTrucks = calcCountBoxInTruckFromJson(filePath);
        terminal.writer().println("Ниже будет указанно сколько и каких посылок было в грузовиках");
        for (Truck truck : countBoxInTrucks.keySet()) {
            terminal.writer().println("---------------------------");
            for (Box box : countBoxInTrucks.get(truck).keySet()) {
                terminal.writer().println(box.toString() + " - количество = " + countBoxInTrucks.get(truck).get(box));
            }
            terminal.writer().println("Вид загруженного грузовика: \n" + truck);
        }
    }

    private Map<Truck, Map<Box, Integer>> calcCountBoxInTruckFromJson(String filePath) {
        List<Truck> trucks = jsonTruckService.readJson(filePath);
        return truckService.countBoxInTrucks(trucks);
    }


}
