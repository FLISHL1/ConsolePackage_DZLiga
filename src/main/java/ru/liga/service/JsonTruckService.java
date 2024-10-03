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

    public void save(List<Truck> truckList) {
        truckRepository.write(truckList);
    }

    public void save(String filePath, List<Truck> truckList) {
        truckRepository.write(filePath, truckList);
    }

    public List<Truck> getAll(String filePath) {
        return truckRepository.read(filePath);
    }

    public List<Truck> getAll() {
        return truckRepository.read();
    }
}
