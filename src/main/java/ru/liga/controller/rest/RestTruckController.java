package ru.liga.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.entity.Box;
import ru.liga.entity.Truck;
import ru.liga.service.truck.TruckService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/trucks/actions")
public class RestTruckController {
    private static final Logger log = LoggerFactory.getLogger(RestTruckController.class);
    private final TruckService truckService;

    public RestTruckController(TruckService truckService) {
        this.truckService = truckService;
    }


    @PostMapping("/count-box-in-trucks")
    private ResponseEntity<Map<Truck, Map<Box, Integer>>> checkCountBoxInTrucks(@RequestParam("trucks") MultipartFile multipartFile) {
        Map<Truck, Map<Box, Integer>> countBoxInTrucks = truckService.calcCountBoxInTruckFromJson(multipartFile);
        return ResponseEntity.ok(countBoxInTrucks);
    }

    @PostMapping("/count-box-in-trucks-local-file")
    private ResponseEntity<Map<Truck, Map<Box, Integer>>> checkCountBoxInTrucks(@RequestParam("filePath") String filePath) {
        Map<Truck, Map<Box, Integer>> countBoxInTrucks = truckService.calcCountBoxInTruckFromJson(filePath);
        return ResponseEntity.ok(countBoxInTrucks);
    }
}
