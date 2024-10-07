package ru.liga.controller;

import org.jline.terminal.Terminal;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.entity.Truck;
import ru.liga.service.FillTruckService;
import ru.liga.truckLoader.TruckLoader;
import ru.liga.validator.FileNameValidator;

import java.util.List;

@ShellComponent
@ShellCommandGroup("Truck-command")
public class ShellTruckLoaderFileCommand {
    private final Terminal terminal;
    private final FileNameValidator fileNameValidator;
    private final FillTruckService fillTruckService;
    private final ShellFileValidator shellFileValidator;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;


    public ShellTruckLoaderFileCommand(Terminal terminal, FileNameValidator fileNameValidator, FillTruckService fillTruckService, ShellFileValidator shellFileValidator, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader) {
        this.terminal = terminal;
        this.fileNameValidator = fileNameValidator;
        this.fillTruckService = fillTruckService;
        this.shellFileValidator = shellFileValidator;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
    }

    @ShellMethod(value = "Загрузка коробок в грузовик из файла максимально эффективным способом", key = "loader-truck-maximal-file")
    private void maximalLoaderTruck(
            @ShellOption(help = "Путь до файла с посылками в формате .txt для погрузка из папки resources", value = {"filePath", "-f"})
            String filePath,
            @ShellOption(help = "Перечисление размеров грузовиков, каждый размер это один грузовик\n Пример: 6x6 12x2", value = {"sizeTrucks", "-s"}, arity = Integer.MAX_VALUE - 1)
            String[] trucksSizes
    ) {

        fillTruckWithBoxes(filePath, trucksSizes, maximalTruckLoader);
    }

    @ShellMethod(value = "Загрузка коробок в грузовик из файла равномерно", key = "loader-truck-uniform-file")
    private void uniformLoaderTruck(
            @ShellOption(help = "Путь до файла с посылками в формате .txt для погрузка из папки resources", value = {"filePath", "-f"})
            String filePath,
            @ShellOption(help = "Перечисление размеров грузовиков, каждый размер это один грузовик\n Пример: 6x6 12x2", value = {"sizeTrucks", "-s"}, arity = Integer.MAX_VALUE - 1)
            String[] trucksSizes
    ) {
        fillTruckWithBoxes(filePath, trucksSizes, uniformTruckLoader);
    }


    private void fillTruckWithBoxes(String filePath, String[] truckSize, TruckLoader truckLoader) {
        shellFileValidator.checkFileName(fileNameValidator.validateTxt(filePath));
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByFile(filePath, truckSize, truckLoader);
        terminal.writer().println("Погруженные грузовики: ");
        for (Truck truck : trucks) {
            terminal.writer().println(truck.toString());
        }
    }

}
