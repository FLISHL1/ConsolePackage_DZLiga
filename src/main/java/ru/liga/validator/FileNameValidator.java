package ru.liga.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FileNameValidator{
    private final static Pattern REGEX_FILENAME_TXT = Pattern.compile("(?!.*/\\.txt$).+\\.txt");
    private final static Pattern REGEX_FILENAME_JSON = Pattern.compile("(?!.*/\\.json$).+\\.json");
    private final static String ERROR_MESSAGE = "Incorrect file name";

    /**
     *
     * @param fileName Путь до файла txt в папке resources
     * @return Результат валидации
     */
    public ValidationResult validateTxt(String fileName){
        if (REGEX_FILENAME_TXT.matcher(fileName).matches()){
            return new ValidationResult(true, "");
        } else {
            return new ValidationResult(false, ERROR_MESSAGE);
        }
    }
    /**
     *
     * @param fileName Путь до файла json в папке resources
     * @return Результат валидации
     */
    public ValidationResult validateJson(String fileName){
        if (REGEX_FILENAME_JSON.matcher(fileName).matches()){
            return new ValidationResult(true, "");
        } else {
            return new ValidationResult(false, ERROR_MESSAGE);
        }
    }
}
