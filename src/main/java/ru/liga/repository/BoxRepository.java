package ru.liga.repository;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.liga.entity.Box;
import ru.liga.exception.IdentityNameBoxException;
import ru.liga.util.Reader;
import ru.liga.util.Writer;

import java.util.List;

@Repository
public class BoxRepository {
    private final Writer<List<Box>> jsonWriter;
    private final Reader<List<Box>> jsonReader;
    private final String filePath;

    public BoxRepository(Writer<List<Box>> jsonWriter, Reader<List<Box>> jsonReader, @Value("${util.json.box.file}") String filePath) {
        this.jsonWriter = jsonWriter;
        this.jsonReader = jsonReader;
        this.filePath = filePath;
    }

    public List<Box> findAll() {
        return jsonReader.read(filePath);
    }

    public @Nullable Box findByName(String name) {
        List<Box> boxes = findAll().stream().filter(box -> box.getName().equalsIgnoreCase(name)).toList();
        if (boxes.size() > 1) {
            throw new IdentityNameBoxException();
        } else if (boxes.isEmpty()) {
            return null;
        }
        return boxes.get(0);
    }

    public void save(Box saveBox) {
        List<Box> boxes = findAll();
        boxes.add(saveBox);
        jsonWriter.write(filePath, boxes);
    }

    public void update(Box updateBox){
        remove(updateBox);
        save(updateBox);
    }


    public void remove(Box box) {
        List<Box> boxes = findAll();
        boxes.remove(box);
        jsonWriter.write(filePath, boxes);
    }

}
