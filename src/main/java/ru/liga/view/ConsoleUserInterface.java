package ru.liga.view;

import ru.liga.controller.UserInputHandler;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.exception.UserInputException;
import ru.liga.truckLoader.MaximalTruckLoader;
import ru.liga.truckLoader.TruckLoader;
import ru.liga.truckLoader.TypeTruckLoader;
import ru.liga.truckLoader.UniformTruckLoader;
import ru.liga.util.TxtParser;
import ru.liga.validator.FileNameValidator;
import ru.liga.validator.ValidationResult;

import java.util.List;

public class ConsoleUserInterface {
    public void start() {
        UserInputHandler userInputHandler = new UserInputHandler();
        String fileName = userInputHandler.prompt("Введите название файла: ");
        ValidationResult fileNameValidation = new FileNameValidator().validate(fileName);
        if (fileNameValidation.isInvalid()) {
            message("Название файла не корректно");
            return;
        }
        String countTrucks = userInputHandler.prompt("Введите количество погружаемых машин: ");

        String loaderType = selectLoaderType(userInputHandler);
        if (!isValidCommand(loaderType, TypeTruckLoader.UNIFORM.toString(), TypeTruckLoader.MAXIMAL.toString())){
            throw new UserInputException();
        }
        TruckLoader truckLoader = getTruckLoader(loaderType);

        TxtParser fileParser = new TxtParser();
        List<Box> boxes = fileParser.parseBoxFromFile(fileName);
        List<Truck> trucks = truckLoader.load(boxes, Integer.parseInt(countTrucks));

        for (Truck truck: trucks){
            System.out.println(truck);
        }
    }
    private String selectLoaderType(UserInputHandler inputHandler) {
        return inputHandler.prompt(String.format("""
            Выберите вариант алгоритма расстановки коробок:
                %s. Равномерная погрузка
                %s. Максимально качественная
            """, TypeTruckLoader.UNIFORM, TypeTruckLoader.MAXIMAL));
    }

    private static TruckLoader getTruckLoader(String typeLoader) {
        if (TypeTruckLoader.UNIFORM.toString().equals(typeLoader)){
            return new UniformTruckLoader();
        } else if (TypeTruckLoader.MAXIMAL.toString().equals(typeLoader)){
            return new MaximalTruckLoader();
        }
        return new UniformTruckLoader();
    }

    private void message(String text) {
        System.out.println(text);
    }

    private Boolean isValidCommand(String text, String... command) {
        if (text.isEmpty()) {
            message("Вы ввели пустую строчку");
            return false;
        } else if (command.length != 0 && !String.join("", command).contains(text)) {
            message("Введена неизвестная команда");
            return false;
        }
        return true;
    }
}
