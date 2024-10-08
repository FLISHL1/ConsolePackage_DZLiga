package ru.liga.controller.shell;

import org.jline.terminal.Terminal;
import org.springframework.shell.standard.ShellComponent;
import ru.liga.exception.UserInputException;
import ru.liga.validator.ValidationResult;

@ShellComponent
public class ShellFileValidator {
    private final Terminal terminal;

    public ShellFileValidator(Terminal terminal) {
        this.terminal = terminal;
    }

    protected void checkFileName(ValidationResult fileNameValidation) {
        if (fileNameValidation.isInvalid()) {
            terminal.writer().println("Название файла не корректно");
            throw new UserInputException(fileNameValidation.getErrorMessage());
        }
    }
}
