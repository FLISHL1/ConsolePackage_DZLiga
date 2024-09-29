package ru.liga.controller;

import org.jline.terminal.Terminal;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.entity.Truck;
import ru.liga.service.TruckService;
import ru.liga.truckLoader.TruckLoader;
import ru.liga.validator.FileNameValidator;

import java.util.List;

@ShellComponent
@ShellCommandGroup("Truck-command")
public class ShellTruckLoaderCommand {
    private final Terminal terminal;
    private final FileNameValidator fileNameValidator;
    private final TruckService truckService;
    private final ShellFileValidator shellFileValidator;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;

    public ShellTruckLoaderCommand(Terminal terminal, FileNameValidator fileNameValidator, TruckService truckService, ShellFileValidator shellFileValidator, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader) {
        this.terminal = terminal;
        this.fileNameValidator = fileNameValidator;
        this.truckService = truckService;
        this.shellFileValidator = shellFileValidator;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
    }

    @ShellMethod(value = "Загрузка коробок в грузовик из файла максимально эффективным способом", key = "maximal-loader-truck")
    private void maximalLoaderTruckWithBox(
            @ShellOption(help = "Путь до файла с посылками в формате .txt для погрузка из папки resources", value = {"filePath", "-f"})
            String filePath,
            @ShellOption(help = "Максимальное количество грузовиков для погрузки", value = {"maxCountTruck", "-mt"})
            Integer maxCountTruck
    ) {
        fillTruckWithBoxes(filePath, maxCountTruck, maximalTruckLoader);
    }

    @ShellMethod(value = "Загрузка коробок в грузовик из файла равномерно", key = "uniform-loader-truck")
    private void uniformLoaderTruckWithBox(
            @ShellOption(help = "Путь до файла с посылками в формате .txt для погрузка из папки resources", value = {"filePath", "-f"})
            String filePath,
            @ShellOption(help = "Максимальное количество грузовиков для погрузки", value = {"maxCountTruck", "-mt"})
            Integer maxCountTruck
    ) {
        fillTruckWithBoxes(filePath, maxCountTruck, uniformTruckLoader);
    }


    private void fillTruckWithBoxes(String filePath, Integer maxCountTruck, TruckLoader truckLoader) {
        shellFileValidator.checkFileName(fileNameValidator.validateTxt(filePath));
        List<Truck> trucks = truckService.fillTruckWithBoxes(filePath, maxCountTruck, truckLoader);
        terminal.writer().println("Погруженные грузовики: ");
        for (Truck truck : trucks) {
            terminal.writer().println(truck.toString());
        }
    }

}
