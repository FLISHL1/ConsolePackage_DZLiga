package ru.liga.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.liga.entity.Truck;
import ru.liga.mapper.TruckSizeMapper;
import ru.liga.service.truck.FillTruckService;
import ru.liga.truckLoader.TruckLoader;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trucks/actions")
public class RestTruckLoaderFileController {
    private final FillTruckService fillTruckService;
    private final TruckLoader maximalTruckLoader;
    private final TruckLoader uniformTruckLoader;

    public RestTruckLoaderFileController(FillTruckService fillTruckService, TruckLoader maximalTruckLoader, TruckLoader uniformTruckLoader) {
        this.fillTruckService = fillTruckService;
        this.maximalTruckLoader = maximalTruckLoader;
        this.uniformTruckLoader = uniformTruckLoader;
    }

    @PostMapping("/truck-loader-maximal-local-file")
    private ResponseEntity<List<Truck>> maximalLoaderTruckLocal(
            @RequestParam("filePathBox") String filePath,
            @RequestParam("truckSizes") String trucksSizes
    ) {
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByFile(filePath, trucksSizes, maximalTruckLoader);
        return ResponseEntity.ok(trucks);
    }

    @PostMapping("/truck-loader-uniform-local-file")
    private ResponseEntity<List<Truck>> uniformLoaderTruckLocal(
            @RequestParam("filePathBox") String filePath,
            @RequestParam("truckSizes") String trucksSizes
    ) {
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByFile(filePath, trucksSizes, uniformTruckLoader);
        return ResponseEntity.ok(trucks);
    }

    @PostMapping("/truck-loader-maximal-file")
    private ResponseEntity<List<Truck>> maximalLoaderTruck(
            @RequestParam("boxes") MultipartFile file,
            @RequestParam("truckSizes") String trucksSizes
    ) {

        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByFile(file, trucksSizes, maximalTruckLoader);
        return ResponseEntity.ok(trucks);
    }

    @PostMapping("/truck-loader-uniform-file")
    private ResponseEntity<List<Truck>> uniformLoaderTruck(
            @RequestParam("boxes") MultipartFile file,
            @RequestParam("truckSizes") String trucksSizes
    ) {
        List<Truck> trucks = fillTruckService.fillTrucksWithBoxesByFile(file, trucksSizes, uniformTruckLoader);
        return ResponseEntity.ok(trucks);
    }

}
