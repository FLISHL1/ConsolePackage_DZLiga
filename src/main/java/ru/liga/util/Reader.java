package ru.liga.util;

import org.springframework.web.multipart.MultipartFile;

public interface Reader<T> {
    T read(String filePath);
    T read(MultipartFile file);

    T readByString(String file);
}
