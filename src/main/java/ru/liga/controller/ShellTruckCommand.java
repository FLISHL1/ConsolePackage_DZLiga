package ru.liga.controller;

import org.jline.terminal.Terminal;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.exceptions.UserInputException;
import ru.liga.service.JsonTruckService;
import ru.liga.service.TruckService;
import ru.liga.truckLoader.MaximalTruckLoader;
import ru.liga.truckLoader.TruckLoader;
import ru.liga.truckLoader.UniformTruckLoader;
import ru.liga.validator.FileNameValidator;
import ru.liga.validator.ValidationResult;

import java.util.List;
import java.util.Map;

@ShellComponent
@ShellCommandGroup("TruckCommand")
public class ShellTruckCommand {
    private final Terminal terminal;
    private final FileNameValidator fileNameValidator;
    private final TruckService truckService;
    private final JsonTruckService jsonTruckService;

    public ShellTruckCommand(Terminal terminal, FileNameValidator fileNameValidator, TruckService truckService, JsonTruckService jsonTruckService) {
        this.terminal = terminal;
        this.fileNameValidator = fileNameValidator;
        this.truckService = truckService;
        this.jsonTruckService = jsonTruckService;
    }


    @ShellMethod(value = "Загрузка коробок в грузовик из файла равномерно", key = "count-box-in-trucks")
    private void checkCountBoxInTrucks(
            @ShellOption(help = "Путь до файла с посылками в формате .json для погрузка из папки resources", value = {"filePath", "-f"}, optOut = true)
            String filePath
    ) {
        ValidationResult fileNameValidation = fileNameValidator.validateJson(filePath);
        checkFileName(fileNameValidation);
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

    private void checkFileName(ValidationResult fileNameValidation) {
        if (fileNameValidation.isInvalid()) {
            terminal.writer().println("Название файла не корректно");
            throw new UserInputException(fileNameValidation.getErrorMessage());
        }
    }
}
