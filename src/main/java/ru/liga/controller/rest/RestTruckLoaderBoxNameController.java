package ru.liga.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.entity.Truck;
import ru.liga.service.truck.FillTruckService;
import ru.liga.truckLoader.TruckLoader;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trucks/actions")
public class RestTruckLoaderBoxNameController {
    private final FillTruckService fillTruckService;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;


    public RestTruckLoaderBoxNameController(FillTruckService fillTruckService, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader) {
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

    @PostMapping("/truck-loader-uniform-box")
    private ResponseEntity<List<Truck>> uniformLoaderTruckBoxNames(
            @RequestParam("boxNames") String boxNames,
            @RequestParam("truckSizes") String trucksSizes
    ) {
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByName(boxNames, trucksSizes, uniformTruckLoader);
        return ResponseEntity.ok(trucks);
    }

}
