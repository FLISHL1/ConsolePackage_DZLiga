package ru.liga.exception;

public class FileDownloadException extends RuntimeException {
    public FileDownloadException() {
    }

    public FileDownloadException(String message) {
        super(message);
    }
}
