package ru.liga.service;

import org.springframework.stereotype.Service;
import ru.liga.entity.Truck;
import ru.liga.repository.TruckRepository;

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
     * Возвращает грузовики
     * @return Список грузовиков
     */
    public List<Truck> getAll() {
        return truckRepository.read();
    }
}
