package ru.liga.controller;

import java.util.Scanner;

public class UserInputHandler {
    private final String COMMAND_EXIT = "exit";
    private final Scanner scanner = new Scanner(System.in);

    public String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public void checkExitCommand(String input) {
        if (input.strip().equals(COMMAND_EXIT)) {
            System.exit(0);
        }
    }
}
