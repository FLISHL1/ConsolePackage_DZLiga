package ru.liga.validator;

public class FileNameValidator{
    private final String REGEX_FILENAME = "(?!.*/\\.txt$).+\\.txt";
    private final String ERROR_MESSAGE = "";

    public ValidationResult validate(String fileName){
        if (fileName.matches(REGEX_FILENAME)){
            return new ValidationResult(true, "");
        } else {
            return new ValidationResult(false, ERROR_MESSAGE);
        }
    }
}
