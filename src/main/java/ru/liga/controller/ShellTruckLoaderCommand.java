package ru.liga.controller;

import org.jline.terminal.Terminal;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.entity.Truck;
import ru.liga.exceptions.UserInputException;
import ru.liga.service.TruckService;
import ru.liga.truckLoader.MaximalTruckLoader;
import ru.liga.truckLoader.TruckLoader;
import ru.liga.truckLoader.UniformTruckLoader;
import ru.liga.validator.FileNameValidator;
import ru.liga.validator.ValidationResult;

import java.util.List;

@ShellComponent
@ShellCommandGroup("TruckCommand")
public class ShellTruckLoaderCommand {
    private final Terminal terminal;
    private final FileNameValidator fileNameValidator;
    private final TruckService truckService;

    public ShellTruckLoaderCommand(Terminal terminal, FileNameValidator fileNameValidator, TruckService truckService) {
        this.terminal = terminal;
        this.fileNameValidator = fileNameValidator;
        this.truckService = truckService;
    }

    @ShellMethod(value = "Загрузка коробок в грузовик из файла максимально эффективным способом", key = "maximal-loader-truck")
    private void maximalLoaderTruckWithBox(
            @ShellOption(help = "Путь до файла с посылками в формате .txt для погрузка из папки resources", value = {"filePath", "-f"})
            String filePath,
            @ShellOption(help = "Максимальное количество грузовиков для погрузки", value = {"maxCountTruck", "-mt"})
            Integer maxCountTruck
    ) {
        fillTruckWithBoxes(filePath, maxCountTruck, new MaximalTruckLoader());
    }

    @ShellMethod(value = "Загрузка коробок в грузовик из файла равномерно", key = "uniform-loader-truck")
    private void uniformLoaderTruckWithBox(
            @ShellOption(help = "Путь до файла с посылками в формате .txt для погрузка из папки resources", value = {"filePath", "-f"}, optOut = true)
            String filePath,
            @ShellOption(help = "Максимальное количество грузовиков для погрузки", value = {"maxCountTruck", "-mt"}, optOut = true)
            Integer maxCountTruck
    ) {
        fillTruckWithBoxes(filePath, maxCountTruck, new UniformTruckLoader());
    }


    private void fillTruckWithBoxes(String filePath, Integer maxCountTruck, TruckLoader truckLoader) {
        ValidationResult fileNameValidation = fileNameValidator.validateTxt(filePath);
        checkFileName(fileNameValidation);
        List<Truck> trucks = truckService.fillTruckWithBoxes(filePath, maxCountTruck, truckLoader);
        terminal.writer().println("Погруженные грузовики: ");
        for (Truck truck : trucks) {
            terminal.writer().println(truck.toString());
        }
    }


    private void checkFileName(ValidationResult fileNameValidation) {
        if (fileNameValidation.isInvalid()) {
            terminal.writer().println("Название файла не корректно");
            throw new UserInputException(fileNameValidation.getErrorMessage());
        }
    }
}
