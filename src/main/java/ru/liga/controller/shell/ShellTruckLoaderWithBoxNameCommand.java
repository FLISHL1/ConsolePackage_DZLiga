package ru.liga.controller.shell;

import org.jline.terminal.Terminal;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.entity.Truck;
import ru.liga.service.truck.FillTruckService;
import ru.liga.truckLoader.TruckLoader;

import java.util.List;

@ShellComponent
@ShellCommandGroup("Truck-command")
public class ShellTruckLoaderWithBoxNameCommand {
    private final Terminal terminal;
    private final FillTruckService fillTruckService;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;


    public ShellTruckLoaderWithBoxNameCommand(Terminal terminal, FillTruckService fillTruckService, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader) {
        this.terminal = terminal;
        this.fillTruckService = fillTruckService;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
    }

    @ShellMethod(value = "Загрузка коробок в грузовик из сохраненных коробок максимально эффективным способом", key = "loader-truck-maximal-box")
    private void maximalLoaderTruck(
            @ShellOption(help = "Названия коробок через ,", value = {"boxes", "-b"}, arity = Integer.MAX_VALUE - 1)
            String[] boxNames,
            @ShellOption(help = "Перечисление размеров грузовиков, каждый размер это один грузовик\n Пример: 6x6 12x2", value = {"sizeTrucks", "-s"}, arity = Integer.MAX_VALUE - 1)
            String[] trucksSizes
    ) {
        fillTruckWithBoxes(boxNames, trucksSizes, maximalTruckLoader);
    }

    @ShellMethod(value = "Загрузка коробок в грузовик из сохраненных коробок равномерно", key = "loader-truck-uniform-box")
    private void uniformLoaderTruck(
            @ShellOption(help = "Названия коробок через ,", value = {"boxes", "-b"}, arity = Integer.MAX_VALUE - 1)
            String[] boxNames,
            @ShellOption(help = "Перечисление размеров грузовиков, каждый размер это один грузовик\n Пример: 6x6 12x2", value = {"sizeTrucks", "-s"}, arity = Integer.MAX_VALUE - 1)
            String[] trucksSizes
    ) {
        fillTruckWithBoxes(boxNames, trucksSizes, uniformTruckLoader);
    }


    private void fillTruckWithBoxes(String[] boxNames, String[] truckSize, TruckLoader truckLoader) {
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByName(boxNames, truckSize, truckLoader);
        terminal.writer().println("Погруженные грузовики: ");
        for (Truck truck : trucks) {
            terminal.writer().println(truck.toString());
        }
    }

}
