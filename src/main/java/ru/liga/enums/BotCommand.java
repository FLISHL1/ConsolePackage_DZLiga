package ru.liga.enums;

public enum BotCommand {
    BOX_GET_ALL("/box_all"),
    BOX_GET_NAME("box_get"),
    BOX_SAVE("/box_add"),
    BOX_UPDATE("/box_update"),
    BOX_DELETE("/box_remove"),
    COUNT_BOX_TRUCK("/count_box_in_trucks"),
    LOAD_TRUCK_UNIFORM_BOX("/loader_truck_uniform_box"),
    LOAD_TRUCK_MAXIMAL_BOX("/loader_truck_maximal_box"),
    LOAD_TRUCK_UNIFORM_FILE("/loader_truck_uniform_file"),
    LOAD_TRUCK_MAXIMAL_FILE("/loader_truck_maximal_file"),
    HELP("/help");


    private final String command;

    BotCommand(String command){
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return command;
    }
}
