package ru.liga.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.entity.Truck;
import ru.liga.service.FillTruckService;
import ru.liga.truckLoader.TruckLoader;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trucks/actions")
public class RestTruckLoaderBoxNameCommand {
    private final FillTruckService fillTruckService;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;


    public RestTruckLoaderBoxNameCommand(FillTruckService fillTruckService, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader) {
        this.fillTruckService = fillTruckService;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
    }


    @PostMapping("/truck-loader-maximal-box")
    private ResponseEntity<List<Truck>> maximalLoaderTruckBoxNames(
            @RequestParam("boxNames") String boxNames,
            @RequestParam("truckSizes") String trucksSizes
    ) {

        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByName(boxNames, trucksSizes, maximalTruckLoader);
        return ResponseEntity.ok(trucks);
    }

    @PostMapping("/truck-loader-unfiform-box")
    private ResponseEntity<List<Truck>> uniformLoaderTruckBoxNames(
            @RequestParam("boxNames") String boxNames,
            @RequestParam("truckSizes") String trucksSizes
    ) {
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByName(boxNames, trucksSizes, uniformTruckLoader);
        return ResponseEntity.ok(trucks);
    }

}
