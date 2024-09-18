package ru.liga.view;

import ru.liga.controller.UserInputHandler;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.exception.UserInputException;
import ru.liga.service.TruckService;
import ru.liga.truckLoader.MaximalTruckLoader;
import ru.liga.truckLoader.TruckLoader;
import ru.liga.truckLoader.TypeTruckLoader;
import ru.liga.truckLoader.UniformTruckLoader;
import ru.liga.util.JsonReader;
import ru.liga.util.JsonWriter;
import ru.liga.util.TxtParser;
import ru.liga.validator.FileNameValidator;
import ru.liga.validator.ValidationResult;
import java.util.*;

public class ConsoleUserInterface {
    private final UserInputHandler userInputHandler = new UserInputHandler();

    public void start() {
        String typeFunc = userInputHandler.prompt("""
                Выберете функцию:
                    1. Погрузка грузовиков посылками из файла .txt
                    2. Определение количества посылок в загруженных грузовиках из файла .json
                """);
        if (!isValidCommand(typeFunc, "1", "2")){
            throw new UserInputException();
        }
        switch (typeFunc){
            case "1" -> workWithBoxesInTxtFile();
            case "2" -> checkCountBoxInTruck();
        }
    }

    private void checkCountBoxInTruck() {
        String fileName = userInputHandler.prompt("Введите название файла (.json): ");
        ValidationResult fileNameValidation = new FileNameValidator().validateJson(fileName);
        if (fileNameValidation.isInvalid()){
            message("Название файла не корректно");
            throw new UserInputException(fileNameValidation.getErrorMessage());
        }
        JsonReader jsonReader = new JsonReader();
        List<Truck> trucks = jsonReader.readTruckList(fileName);
        TruckService truckService = new TruckService();
        Map<Box, Integer> countBoxInTrucks = truckService.countBoxInTrucks(trucks);
        message("Ниже будет указанно сколько и каких посылок было в грузовиках");
        for (Box box: countBoxInTrucks.keySet()){
            message(box.toString() + " - количество = " + countBoxInTrucks.get(box));
        }
    }

    private void workWithBoxesInTxtFile() {
        String fileName = userInputHandler.prompt("Введите название файла (.txt): ");
        ValidationResult fileNameValidation = new FileNameValidator().validateTxt(fileName);
        if (fileNameValidation.isInvalid()) {
            message("Название файла не корректно");
            throw new UserInputException(fileNameValidation.getErrorMessage());
        }
        String countTrucks = userInputHandler.prompt("Введите количество погружаемых машин: ");

        String loaderType = selectLoaderType();
        if (!isValidCommand(loaderType, TypeTruckLoader.UNIFORM.toString(), TypeTruckLoader.MAXIMAL.toString())) {
            throw new UserInputException();
        }
        TruckLoader truckLoader = getTruckLoader(loaderType);

        TxtParser fileParser = new TxtParser();
        List<Box> boxes = fileParser.parseBoxFromFile(fileName);
        List<Truck> trucks = truckLoader.load(boxes, Integer.parseInt(countTrucks));
        JsonWriter jsonWriter = new JsonWriter();
        jsonWriter.writeToFileTruckList(trucks);
        message("Погруженные грузовики: ");
        for (Truck truck : trucks) {
            message(truck.toString());
        }
    }

    private String selectLoaderType() {
        return userInputHandler.prompt(String.format("""
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
