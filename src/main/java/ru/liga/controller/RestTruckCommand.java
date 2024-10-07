package ru.liga.controller;

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
import ru.liga.service.TruckService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/trucks/actions")
public class RestTruckCommand {
    private static final Logger log = LoggerFactory.getLogger(RestTruckCommand.class);
    private final TruckService truckService;

    public RestTruckCommand(TruckService truckService) {
        this.truckService = truckService;
    }


    @PostMapping("/count-box-in-trucks")
    private ResponseEntity<Map<Truck, Map<Box, Integer>>> checkCountBoxInTrucks(@RequestParam("trucks") MultipartFile multipartFile) {
        log.debug("{}", multipartFile);
        Map<Truck, Map<Box, Integer>> countBoxInTrucks = truckService.calcCountBoxInTruckFromJson(multipartFile);
        return ResponseEntity.ok(countBoxInTrucks);
    }

    @PostMapping("/count-box-in-trucks-local-file")
    private ResponseEntity<Map<Truck, Map<Box, Integer>>> checkCountBoxInTrucks(@RequestParam("filePath") String filePath) {
        Map<Truck, Map<Box, Integer>> countBoxInTrucks = truckService.calcCountBoxInTruckFromJson(filePath);
        return ResponseEntity.ok(countBoxInTrucks);
    }
}
