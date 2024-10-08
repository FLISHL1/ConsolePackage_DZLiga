package ru.liga.service.truck;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.entity.Truck;
import ru.liga.exception.ReadJsonException;
import ru.liga.repository.TruckRepository;

import java.io.IOException;
import java.util.List;

@Service
public class JsonTruckService {
    private final TruckRepository truckRepository;

    public JsonTruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    /**
     * Сохраняет грузовики в файл
     * @param truckList Список грузовиков
     */
    public void save(List<Truck> truckList) {
        truckRepository.write(truckList);
    }
    /**
     * Сохраняет грузовики в файл
     * @param filePath Файл куда сохранять грузовики
     * @param truckList Список грузовиков
     */
    public void save(String filePath, List<Truck> truckList) {
        truckRepository.write(filePath, truckList);
    }
    /**
     * Читает грузовики из файла
     * @param filePath Файл откуда читать грузовики
     * @return Список грузовиков в файле
     */
    public List<Truck> getAll(String filePath) {
        return truckRepository.read(filePath);
    }

    /**
     * Читает грузовики из файла
     * @param file Файл в строке грузовики
     * @return Список грузовиков в файле
     */
    public List<Truck> getAllByString(String file) {
        return truckRepository.readByString(file);
    }


    /**
     * Читает грузовики из строчки json
     * @param multipartJson
     * @return Список грузовиков в файле
     */
    public List<Truck> getAll(MultipartFile multipartJson) {
        try {
            String fileJson = new String(multipartJson.getBytes());
            return truckRepository.readByString(fileJson);
        } catch (IOException e) {
            throw new ReadJsonException(e.getMessage());
        }
    }

    /**
     * Возвращает грузовики
     * @return Список грузовиков
     */
    public List<Truck> getAll() {
        return truckRepository.read();
    }
}
