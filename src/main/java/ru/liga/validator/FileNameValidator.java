package ru.liga.validator;

public class FileNameValidator{
    private final String REGEX_FILENAME_TXT = "(?!.*/\\.txt$).+\\.txt";
    private final String REGEX_FILENAME_JSON = "(?!.*/\\.json$).+\\.json";
    private final String ERROR_MESSAGE = "Incorrect file name";

    /**
     *
     * @param fileName Путь до файла txt в папке resources
     * @return Результат валидации
     */
    public ValidationResult validateTxt(String fileName){
        if (fileName.matches(REGEX_FILENAME_TXT)){
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
        if (fileName.matches(REGEX_FILENAME_JSON)){
            return new ValidationResult(true, "");
        } else {
            return new ValidationResult(false, ERROR_MESSAGE);
        }
    }
}
