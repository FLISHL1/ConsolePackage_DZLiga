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
public class ShellTruckLoaderSimpleCommand {
    private final Terminal terminal;
    private final FileNameValidator fileNameValidator;
    private final TruckService truckService;
    private final ShellFileValidator shellFileValidator;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;


    public ShellTruckLoaderSimpleCommand(Terminal terminal, FileNameValidator fileNameValidator, TruckService truckService, ShellFileValidator shellFileValidator, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader) {
        this.terminal = terminal;
        this.fileNameValidator = fileNameValidator;
        this.truckService = truckService;
        this.shellFileValidator = shellFileValidator;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
    }

    @ShellMethod(value = "Загрузка коробок в грузовик из файла максимально эффективным способом", key = "loader-truck-maximal-file")
    public void maximalLoaderTruck(
            @ShellOption(help = "Путь до файла с посылками в формате .txt для погрузка из папки resources", value = {"filePath", "-f"})
            String filePath,
            @ShellOption(help = "Максимальное количество грузовиков для погрузки", value = {"maxCountTruck", "-m"})
            Integer maxCountTruck,
            @ShellOption(help = "Ширина багажника грузовика", value = {"widthTruck"}, defaultValue = "6")
            Integer width,
            @ShellOption(help = "Высота багажника грузовика", value = {"heightTruck"}, defaultValue = "6")
            Integer height
    ) {
        fillTruckWithBoxes(filePath, maxCountTruck, maximalTruckLoader, width, height);
    }

    @ShellMethod(value = "Загрузка коробок в грузовик из файла равномерно", key = "loader-truck-uniform-file")
    public void uniformLoaderTruck(
            @ShellOption(help = "Путь до файла с посылками в формате .txt для погрузка из папки resources", value = {"filePath", "-f"})
            String filePath,
            @ShellOption(help = "Максимальное количество грузовиков для погрузки", value = {"maxCountTruck", "-m"})
            Integer maxCountTruck,
            @ShellOption(help = "Указывает ширину багажника грузовика", value = {"widthTruck"}, defaultValue = "6")
            Integer width,
            @ShellOption(help = "Указывает высоту багажника грузовика", value = {"heightTruck"}, defaultValue = "6")
            Integer height
    ) {
        fillTruckWithBoxes(filePath, maxCountTruck, uniformTruckLoader, width, height);
    }


    private void fillTruckWithBoxes(String filePath, Integer maxCountTruck, TruckLoader truckLoader, int width, int height) {
        shellFileValidator.checkFileName(fileNameValidator.validateTxt(filePath));
        List<Truck> trucks = truckService.fillTrucksWithBoxesByName(filePath, maxCountTruck, width, height, truckLoader);
        terminal.writer().println("Погруженные грузовики: ");
        for (Truck truck : trucks) {
            terminal.writer().println(truck.toString());
        }
    }

}
