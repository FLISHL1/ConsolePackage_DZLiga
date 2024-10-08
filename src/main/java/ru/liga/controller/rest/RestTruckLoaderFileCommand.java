package ru.liga.controller.rest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.entity.Truck;
import ru.liga.mapper.Mapper;
import ru.liga.service.truck.FillTruckService;
import ru.liga.truckLoader.TruckLoader;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trucks/actions")
public class RestTruckLoaderFileCommand {
    private final FillTruckService fillTruckService;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;
    private final Mapper<String[], String> truckSizeMap;

    public RestTruckLoaderFileCommand(FillTruckService fillTruckService, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader, @Qualifier("truckSizeMapper") Mapper<String[], String> truckSizeMap) {
        this.fillTruckService = fillTruckService;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
        this.truckSizeMap = truckSizeMap;
    }

    @PostMapping("/truck-loader-maximal-local-file")
    private ResponseEntity<List<Truck>> maximalLoaderTruckLocal(
            @RequestParam("filePathBox") String filePath,
            @RequestParam("truckSizes") String trucksSizes
    ) {
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByFile(filePath, truckSizeMap.map(trucksSizes), maximalTruckLoader);
        return ResponseEntity.ok(trucks);
    }

    @PostMapping("/truck-loader-unfiform-local-file")
    private ResponseEntity<List<Truck>> uniformLoaderTruckLocal(
            @RequestParam("filePathBox") String filePath,
            @RequestParam("truckSizes") String trucksSizes
    ) {
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByFile(filePath, truckSizeMap.map(trucksSizes), uniformTruckLoader);
        return ResponseEntity.ok(trucks);
    }

    @PostMapping("/truck-loader-maximal-file")
    private ResponseEntity<List<Truck>> maximalLoaderTruck(
            @RequestParam("boxes") MultipartFile file,
            @RequestParam("truckSizes") String trucksSizes
    ) {

        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByFile(file, truckSizeMap.map(trucksSizes), maximalTruckLoader);
        return ResponseEntity.ok(trucks);
    }

    @PostMapping("/truck-loader-unfiform-file")
    private ResponseEntity<List<Truck>> uniformLoaderTruck(
            @RequestParam("boxes") MultipartFile file,
            @RequestParam("truckSizes") String trucksSizes
    ) {
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByFile(file, truckSizeMap.map(trucksSizes), uniformTruckLoader);
        return ResponseEntity.ok(trucks);
    }

}
