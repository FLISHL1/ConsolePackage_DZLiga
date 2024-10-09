package ru.liga.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.controller.bot.BotBoxController;
import ru.liga.controller.bot.BotTruckController;
import ru.liga.controller.bot.BotTruckLoaderFileController;
import ru.liga.controller.bot.BotTruckLoaderWithBoxNameController;
import ru.liga.enums.BotCommand;
import ru.liga.exception.FileDownloadException;
import ru.liga.exception.UserInputException;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);
    private final BotBoxController botBoxController;
    private final BotTruckController botTruckController;
    private final BotTruckLoaderFileController botTruckLoaderFileController;
    private final BotTruckLoaderWithBoxNameController botTruckLoaderBoxNameCommand;
    private final String botUsername;
    private final String botToken;

    public TelegramBot(@Value("${bot.token}") String token, @Value("${bot.name}") String botName, TelegramBotsApi telegramBotsApi, BotBoxController botBoxController, BotTruckController botTruckController, BotTruckLoaderFileController botTruckLoaderFileController, BotTruckLoaderWithBoxNameController botTruckLoaderBoxNameCommand) throws TelegramApiException {
        super(token);
        this.botToken = token;
        this.botUsername = botName;
        this.botBoxController = botBoxController;
        this.botTruckController = botTruckController;
        this.botTruckLoaderFileController = botTruckLoaderFileController;
        this.botTruckLoaderBoxNameCommand = botTruckLoaderBoxNameCommand;
        telegramBotsApi.registerBot(this);
    }

    private static boolean checkCommand(BotCommand command, String messageText) {
        return messageText.startsWith(command.getCommand());
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            try {
                Long chatId = update.getMessage().getChatId();
                if (update.getMessage().hasText()) {
                    String messageText = update.getMessage().getText();
                    handleTextMessage(chatId, messageText);
                }
                if (update.getMessage().hasDocument()) {
                    Document document = update.getMessage().getDocument();
                    String file = downloadFileString(document);
                    handleFileMessage(chatId, file, update.getMessage().getCaption());
                }
            } catch (UserInputException e) {
                sendMessage(update.getMessage().getChatId(), "Произошла ошибка, проверьте /help");
                log.debug("{}", e.getMessage());
            }
        }

    }

    private String[] mapDetails(String boxDetails) {
        return boxDetails.split(";");
    }

    private String downloadFileString(Document document) {
        GetFile file = new GetFile(document.getFileId());
        try {
            File fileR = execute(file);
            try (InputStream fileRequest = downloadFileAsStream(fileR)) {
                return new String(fileRequest.readAllBytes());
            }
        } catch (TelegramApiException | IOException e) {
            throw new FileDownloadException(e.getMessage());
        }
    }

    private void handleFileMessage(Long chatId, String file, String messageText) {
        if (checkCommand(BotCommand.COUNT_BOX_TRUCK, messageText)) {
            sendMessage(chatId, botTruckController.checkCountBoxInTrucks(file));
        } else if (checkCommand(BotCommand.LOAD_TRUCK_MAXIMAL_FILE, messageText)) {
            sendMessage(chatId, botTruckLoaderFileController.maximalLoaderTruck(file, removeCommand(BotCommand.LOAD_TRUCK_MAXIMAL_FILE, messageText)));
        } else if (checkCommand(BotCommand.LOAD_TRUCK_UNIFORM_FILE, messageText)) {
            sendMessage(chatId, botTruckLoaderFileController.uniformLoaderTruck(file, removeCommand(BotCommand.LOAD_TRUCK_UNIFORM_FILE, messageText)));
        }
    }

    private void handleTextMessage(Long chatId, String messageText) {
        if (checkCommand(BotCommand.BOX_GET_ALL, messageText)) {
            sendMessage(chatId, botBoxController.getAll());
        } else if (checkCommand(BotCommand.BOX_GET_NAME, messageText)) {
            String boxName = removeCommand(BotCommand.BOX_GET_NAME, messageText);
            sendMessage(chatId, botBoxController.getByName(boxName));
        } else if (checkCommand(BotCommand.BOX_SAVE, messageText)) {
            sendMessage(chatId, botBoxController.save(removeCommand(BotCommand.BOX_SAVE, messageText)));
        } else if (checkCommand(BotCommand.BOX_UPDATE, messageText)) {
            sendMessage(chatId, botBoxController.update(removeCommand(BotCommand.BOX_UPDATE, messageText)));
        } else if (checkCommand(BotCommand.BOX_DELETE, messageText)) {
            sendMessage(chatId, botBoxController.delete(removeCommand(BotCommand.BOX_DELETE, messageText)));
        } else if (checkCommand(BotCommand.HELP, messageText)) {
            handleHelp(chatId);
        } else if (checkCommand(BotCommand.LOAD_TRUCK_MAXIMAL_BOX, messageText)) {
            String[] details = mapDetails(removeCommand(BotCommand.LOAD_TRUCK_MAXIMAL_BOX, messageText));
            if (details.length < 2) {
                throw new UserInputException();
            }
            sendMessage(chatId, botTruckLoaderBoxNameCommand.maximalLoaderTruck(details[0].trim(), details[1].trim()));
        } else if (checkCommand(BotCommand.LOAD_TRUCK_UNIFORM_BOX, messageText)) {
            String[] details = mapDetails(removeCommand(BotCommand.LOAD_TRUCK_UNIFORM_BOX, messageText));
            if (details.length < 2) {
                throw new UserInputException();
            }
            sendMessage(chatId, botTruckLoaderBoxNameCommand.uniformLoaderTruck(details[0].trim(), details[1].trim()));
        } else {
            sendMessage(chatId, "Неизвестная команда. Введите " + BotCommand.HELP);
        }
    }

    private void handleHelp(Long chatId) {
        sendMessage(chatId, """
                Box-service
                       /box_all: Выводит все сохраненные типы коробок
                       /box_add: Сохраняет тип коробки
                       /box_update: Обновляет полностью тип коробки
                       /box_get: Выводит тип коробки
                       /box_remove: Удаляет тип коробки
                Truck-command
                       /loader_truck_uniform_box: Загрузка коробок в грузовик из сохраненных коробок равномерно
                       /loader_truck_uniform_file: Загрузка коробок в грузовик из файла равномерно
                       /loader_truck_maximal_file: Загрузка коробок в грузовик из файла максимально эффективным способом
                       /loader_truck_maximal_box: Загрузка коробок в грузовик из сохраненных коробок максимально эффективным способом
                       /count_box_in_trucks: Подсчет коробок в грузовиках
                Ввод нескольких аргументов проходит по примеру /box_update test; testNew; 1,1,1 1,1; &
                """);
    }

    private String removeCommand(BotCommand command, String message) {
        return message.replace(command.getCommand(), "").trim();
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
