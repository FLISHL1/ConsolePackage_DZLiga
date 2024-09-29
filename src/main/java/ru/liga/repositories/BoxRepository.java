package ru.liga.repositories;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Repository;
import ru.liga.entity.Box;
import ru.liga.exceptions.BoxNotFoundException;
import ru.liga.exceptions.IdentityNameBoxException;
import ru.liga.util.JsonBoxReader;
import ru.liga.util.JsonBoxWriter;

import java.util.List;

@Repository
public class BoxRepository {
    private final JsonBoxWriter jsonWriter;
    private final JsonBoxReader jsonReader;

    public BoxRepository(JsonBoxWriter jsonWriter, JsonBoxReader jsonReader) {
        this.jsonWriter = jsonWriter;
        this.jsonReader = jsonReader;
    }

    public List<Box> findAll() {
        return jsonReader.read();
    }

    public @Nullable Box findByName(String name) {
        List<Box> boxes = findAll().stream().filter(box -> box.getName().equals(name)).toList();
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
        jsonWriter.writeToFile(boxes);
    }

    public void update(Box updateBox){
        remove(updateBox);
        save(updateBox);
    }


    public void remove(Box box) {
        List<Box> boxes = findAll();
        boxes.remove(box);
        jsonWriter.writeToFile(boxes);
    }

}
