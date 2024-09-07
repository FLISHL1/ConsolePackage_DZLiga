package ru.liga;

import javax.naming.directory.InvalidAttributesException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите название файла: ");
        String fileName = scanner.nextLine();
        try {
            if (!fileName.matches("(?!.*/\\.txt$).+\\.txt"))
                throw new InvalidAttributesException();
        } catch (InvalidAttributesException e){
            System.out.println("Названия файла не корректно!");
            System.exit(1);
        }
        ArrayList<Box> boxList = null;
        try {
            boxList = readBoxesFromFile("src/main/resources/test3.txt");
        } catch (InvalidAttributesException e){
            System.out.println("Ошибочный размер коробки");
            System.exit(1);
        }

        boxList = sortBoxList(boxList);

        System.out.println(""" 
                            Выберете вариант алгоритма рассттановки коробок:
                                1. Простой алгоритм
                                2. Эффективный
                            """);
        List<Truck> truckList = switch (scanner.nextLine()){
            case "1" -> loadTruckWithBoxSimple(boxList);
            case "2" -> loadTruckWithBox(boxList);
            default -> throw new IllegalStateException("Unexpected value: " + scanner.nextLine());
        };

        for (Truck truck: truckList){
            System.out.println(truck);
        }

    }

    private static List<Truck> loadTruckWithBoxSimple(ArrayList<Box> boxList) {
        List<Truck> truckList = new ArrayList<>();
        for (Box box: boxList){
            Truck truck = new Truck();
            truck.addBox(box, 0, 0);
            truckList.add(truck);
        }
        return truckList;
    }

    private static ArrayList<Box> sortBoxList(ArrayList<Box> boxList) {
        return (ArrayList<Box>) boxList.stream()
                .sorted((box1, box2) -> Integer.compare(box2.getVolume(), box1.getVolume()))
                .collect(Collectors.toList());
    }

    public static List<Truck> loadTruckWithBox(ArrayList<Box> boxList) {
        List<Truck> truckList = new ArrayList<>();
        while (!boxList.isEmpty()) {
            int fixY = 0;
            Truck truck = new Truck();
            List<Box> unUsedBox = new ArrayList<>();
            while (!boxList.isEmpty()) {
                Box box = boxList.remove(0);
                if (fixY + box.getLength() > Truck.getHeight()) {
                    unUsedBox.add(box);
                    continue;
                }
                truck.addBox(box, 0, fixY);
                int fixYNext = fixY;
                fixY += box.getLength();

                int fixX = box.getWidth();
                while (!boxList.isEmpty()) {
                    int remainingSpaceY = Truck.getHeight() - box.getLength();
                    int remainingSpaceX = Truck.getWidth() - fixX;
                    List<Box> nextBoxList = boxList.stream().filter(boxF -> isNextBox(boxF, remainingSpaceY, remainingSpaceX)).toList();
                    if (nextBoxList.isEmpty()) break;

                    Box nextBox = nextBoxList.get(0);

                    boxList.remove(nextBox);
                    truck.addBox(nextBox, fixX, fixYNext);

                    if (remainingSpaceX > nextBox.getWidth()) {
                        fixX += nextBox.getWidth();
                    } else {
                        fixYNext += nextBox.getLength();
                        if (fixYNext >= fixY) break;
                    }
                }

            }
            boxList.addAll(unUsedBox);
            truckList.add(truck);
        }
        return truckList;
    }


    /**
     * Проверяет помещается ли коробка в оставшееся пространство
     *
     * @param box
     * @param remainingSpaceY
     * @param remainingSpaceX
     * @return bool
     */
    private static boolean isNextBox(Box box, int remainingSpaceY, int remainingSpaceX) {
        return box.getVolume() <= (remainingSpaceY * remainingSpaceX)
                && box.getWidth() <= remainingSpaceX
                && box.getLength() <= remainingSpaceY;
    }

    public static ArrayList<Box> readBoxesFromFile(String fileName) throws FileNotFoundException, InvalidAttributesException {
        FileReader reader = new FileReader(fileName);
        Scanner scanFile = new Scanner(reader);
        ArrayList<Box> boxList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> boxSpace = new ArrayList<>();
        while (scanFile.hasNext()){
            String line = scanFile.nextLine();
            if (line.isBlank()) {
                boxSpace = addBox(boxSpace, boxList);
                continue;
            }
            ArrayList<Integer> lineInt = (ArrayList<Integer>) Arrays.stream(
                    line.strip()
                    .split("")
                    ).map(Integer::parseInt).collect(Collectors.toList());
            boxSpace.add(lineInt);
            if (lineInt.size() > Truck.getWidth() || boxSpace.size() > Truck.getHeight())
                throw new InvalidAttributesException();
        }
        addBox(boxSpace, boxList);
        return boxList;
    }

    private static ArrayList<ArrayList<Integer>> addBox(ArrayList<ArrayList<Integer>> boxSpace, ArrayList<Box> boxList) {
        if (!boxSpace.isEmpty()){
            boxList.add(new Box(boxSpace));
            return new ArrayList<>();
        }
        return boxSpace;
    }


}