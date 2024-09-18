package ru.liga;

import ru.liga.view.ConsoleUserInterface;

import java.io.FileNotFoundException;

public class Application {



    public static void main(String[] args) throws FileNotFoundException{
        new ConsoleUserInterface().start();
    }

/*    private static List<Truck> loadTruckWithBoxSimple(List<Box> boxList) {
        List<Truck> truckList = new ArrayList<>();
        TruckLoader truckLoader = new TruckLoader();
        for (Box box: boxList){
            Truck truck = new Truck();
            truckLoader.addBox(truck, box, 0, 0);
            truckList.add(truck);
        }
        return truckList;
    }*/





}