package ru.liga.validator;

public class ValidationResult {
    private final String errorMessage;
    private final boolean valid;


    public ValidationResult(boolean valid, String errorMessage){
        this.errorMessage = errorMessage;
        this.valid = valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isInvalid() {
        return !valid;
    }
}
