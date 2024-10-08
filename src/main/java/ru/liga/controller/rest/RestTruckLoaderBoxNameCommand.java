package ru.liga.controller.rest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.entity.Truck;
import ru.liga.mapper.Mapper;
import ru.liga.service.truck.FillTruckService;
import ru.liga.truckLoader.TruckLoader;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/trucks/actions")
public class RestTruckLoaderBoxNameCommand {
    private final FillTruckService fillTruckService;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;
    private final Mapper<String[], String> mapBoxName;
    private final Mapper<String[], String> mapTruckSize;

    public RestTruckLoaderBoxNameCommand(FillTruckService fillTruckService, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader, @Qualifier("boxNameMapper") Mapper<String[], String> mapBoxName, @Qualifier("truckSizeMapper") Mapper<String[], String> mapTruckSize) {
        this.fillTruckService = fillTruckService;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
        this.mapBoxName = mapBoxName;
        this.mapTruckSize = mapTruckSize;
    }


    @PostMapping("/truck-loader-maximal-box")
    private ResponseEntity<List<Truck>> maximalLoaderTruckBoxNames(
            @RequestParam("boxNames") String boxNames,
            @RequestParam("truckSizes") String trucksSizes
    ) {

        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByName(mapBoxName.map(boxNames), mapTruckSize.map(trucksSizes), maximalTruckLoader);
        return ResponseEntity.ok(trucks);
    }

    @PostMapping("/truck-loader-unfiform-box")
    private ResponseEntity<List<Truck>> uniformLoaderTruckBoxNames(
            @RequestParam("boxNames") String boxNames,
            @RequestParam("truckSizes") String trucksSizes
    ) {
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByName(mapBoxName.map(boxNames), mapBoxName.map(boxNames), uniformTruckLoader);
        return ResponseEntity.ok(trucks);
    }

}
