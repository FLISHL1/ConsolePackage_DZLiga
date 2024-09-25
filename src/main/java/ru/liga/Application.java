package ru.liga;

import ru.liga.view.ConsoleUserInterface;

import java.io.FileNotFoundException;

public class Application {
    public static void main(String[] args) throws FileNotFoundException{
        new ConsoleUserInterface().start();
    }
}