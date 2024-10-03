package ru.liga.controller;

import org.jline.terminal.Terminal;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.entity.Truck;
import ru.liga.service.TruckService;
import ru.liga.truckLoader.TruckLoader;

import java.util.List;

@ShellComponent
@ShellCommandGroup("Truck-command")
public class ShellTruckLoaderWithBoxNameCommand {
    private final Terminal terminal;
    private final TruckService truckService;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;


    public ShellTruckLoaderWithBoxNameCommand(Terminal terminal, TruckService truckService, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader) {
        this.terminal = terminal;
        this.truckService = truckService;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
    }

    @ShellMethod(value = "Загрузка коробок в грузовик из сохраненных коробок максимально эффективным способом", key = "loader-truck-maximal-box")
    public void maximalLoaderTruck(
            @ShellOption(help = "Названия коробок через ,", value = {"boxes", "-b"}, arity = Integer.MAX_VALUE - 1)
            String[] boxNames,
            @ShellOption(help = "Максимальное количество грузовиков для погрузки", value = {"maxCountTruck", "-m"})
            Integer maxCountTruck,
            @ShellOption(help = "Ширина багажника грузовика", value = {"widthTruck"}, defaultValue = "6")
            Integer width,
            @ShellOption(help = "Высота багажника грузовика", value = {"heightTruck"}, defaultValue = "6")
            Integer height
    ) {
        fillTruckWithBoxes(boxNames, maxCountTruck, maximalTruckLoader, width, height);
    }

    @ShellMethod(value = "Загрузка коробок в грузовик из сохраненных коробок равномерно", key = "loader-truck-uniform-box")
    public void uniformLoaderTruck(
            @ShellOption(help = "Названия коробок через ,", value = {"boxes", "-b"}, arity = Integer.MAX_VALUE - 1)
            String[] boxNames,
            @ShellOption(help = "Максимальное количество грузовиков для погрузки", value = {"maxCountTruck", "-m"})
            Integer maxCountTruck,
            @ShellOption(help = "Указывает ширину багажника грузовика", value = {"widthTruck"}, defaultValue = "6")
            Integer width,
            @ShellOption(help = "Указывает высоту багажника грузовика", value = {"heightTruck"}, defaultValue = "6")
            Integer height
    ) {
        fillTruckWithBoxes(boxNames, maxCountTruck, uniformTruckLoader, width, height);
    }


    private void fillTruckWithBoxes(String[] boxNames, Integer maxCountTruck, TruckLoader truckLoader, int width, int height) {
        List<Truck> trucks = truckService.fillTrucksWithBoxesByName(boxNames, maxCountTruck, width, height, truckLoader);
        terminal.writer().println("Погруженные грузовики: ");
        for (Truck truck : trucks) {
            terminal.writer().println(truck.toString());
        }
    }

}
