package ru.liga.controller.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.BoxDtoRequest;
import ru.liga.dto.BoxDtoResponse;
import ru.liga.service.box.BoxService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boxes")
public class RestBoxController {
    private final BoxService boxService;

    public RestBoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping()
    private ResponseEntity<List<BoxDtoResponse>> getAll() {
        List<BoxDtoResponse> boxes = boxService.getAll();
        return ResponseEntity.ok(boxes);
    }

    @GetMapping("/{name}")
    private ResponseEntity<BoxDtoResponse> getByName(@PathVariable("name") @NotBlank String name) {
        BoxDtoResponse box = boxService.getByName(name);
        return ResponseEntity.ok(box);
    }

    @PostMapping
    private ResponseEntity<BoxDtoResponse> save(@RequestBody BoxDtoRequest boxDto) {
        BoxDtoResponse box = boxService.save(boxDto);
        return ResponseEntity.ok(box);
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody @Valid BoxDtoRequest boxDtoRequest) {
        BoxDtoResponse box = boxService.update(id, boxDtoRequest);
        return ResponseEntity.ok(box);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<?> updatePart(@PathVariable("id") Integer id, @RequestBody BoxDtoRequest boxDtoRequest) {
        BoxDtoResponse box = boxService.updatePart(id, boxDtoRequest);
        return ResponseEntity.ok(box);
    }

    @DeleteMapping("/{name}")
    private ResponseEntity<?> remove(@PathVariable("name") @NotBlank String name) {
        boxService.remove(name);
        return ResponseEntity.ok().build();
    }
}
