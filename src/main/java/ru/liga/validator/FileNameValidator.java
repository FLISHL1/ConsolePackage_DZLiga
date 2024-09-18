package ru.liga.validator;

public class FileNameValidator{
    private final String REGEX_FILENAME_TXT = "(?!.*/\\.txt$).+\\.txt";
    private final String REGEX_FILENAME_JSON = "(?!.*/\\.json$).+\\.json";
    private final String ERROR_MESSAGE = "Incorrect file name";

    public ValidationResult validateTxt(String fileName){
        if (fileName.matches(REGEX_FILENAME_TXT)){
            return new ValidationResult(true, "");
        } else {
            return new ValidationResult(false, ERROR_MESSAGE);
        }
    }
    public ValidationResult validateJson(String fileName){
        if (fileName.matches(REGEX_FILENAME_JSON)){
            return new ValidationResult(true, "");
        } else {
            return new ValidationResult(false, ERROR_MESSAGE);
        }
    }
}
