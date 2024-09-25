package ru.liga.controller;

import java.util.Scanner;

public class UserInputHandler {
    private final String COMMAND_EXIT = "exit";
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Пользовательский ввод в консоль
     *
     * @param message Сообщение  в консоль, приглашение для ввода
     * @return Введенную пользователем строку
     */
    public String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    /**
     * Проверяет {@code input} на соответствие команды выхода <p>
     * В случае успешной проверки завершает работу программы
     *
     * @param input - Проверяемая строчка
     */
    public void checkExitCommand(String input) {
        if (input.strip().equals(COMMAND_EXIT)) {
            System.exit(0);
        }
    }
}
