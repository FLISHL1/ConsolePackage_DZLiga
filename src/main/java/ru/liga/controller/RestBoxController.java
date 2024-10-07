package ru.liga.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.BoxDtoRequest;
import ru.liga.entity.Box;
import ru.liga.exception.BoxNotFoundException;
import ru.liga.exception.UserInputException;
import ru.liga.service.BoxService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/boxes")
public class RestBoxController {
    private final BoxService boxService;

    public RestBoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping()
    private ResponseEntity<List<Box>> getAll() {
        List<Box> boxes = boxService.getAll();
        return ResponseEntity.ok(boxes);
    }

    @GetMapping("/{name}")
    private ResponseEntity<Box> getByName(@PathVariable("name") @NotBlank String name) {
        Box box = boxService.getByName(name).orElseThrow(BoxNotFoundException::new);
        return ResponseEntity.ok(box);
    }

    @PostMapping
    private ResponseEntity<Box> save(@RequestBody BoxDtoRequest boxDto) {
        Box box = new Box(boxDto.getName(), boxDto.getSpace());
        box = boxService.save(box);
        return ResponseEntity.ok(box);
    }

    @PutMapping("/{name}")
    private ResponseEntity update(
            @PathVariable("name") @NotBlank String name,
            @RequestBody BoxDtoRequest boxDtoRequest
    ) {
        Optional<String> newNameOpt = Optional.ofNullable(boxDtoRequest.getName());
        Optional<List<List<String>>> spaceOpt = Optional.ofNullable(boxDtoRequest.getSpace());
        Optional<String> charSpaceOpt = Optional.ofNullable(boxDtoRequest.getCharSpace());

        Box box = boxService.getByName(name).orElseThrow(BoxNotFoundException::new);
        spaceOpt.ifPresentOrElse(box::setSpace, UserInputException::new);
        charSpaceOpt.ifPresentOrElse((ch) -> boxService.changeCharSpace(box, ch), UserInputException::new);
        newNameOpt.ifPresentOrElse(box::setName, UserInputException::new);
        boxService.update(box);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{name}")
    private ResponseEntity updatePart(
            @PathVariable("name") @NotBlank String name,
            @RequestBody BoxDtoRequest boxDtoRequest
    ) {
        Optional<String> newNameOpt = Optional.ofNullable(boxDtoRequest.getName());
        Optional<List<List<String>>> spaceOpt = Optional.ofNullable(boxDtoRequest.getSpace());
        Optional<String> charSpaceOpt = Optional.ofNullable(boxDtoRequest.getCharSpace());

        Box box = boxService.getByName(name).orElseThrow(BoxNotFoundException::new);
        spaceOpt.ifPresent(box::setSpace);
        charSpaceOpt.ifPresent((ch) -> boxService.changeCharSpace(box, ch));
        newNameOpt.ifPresent(box::setName);
        boxService.update(box);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{name}")
    private ResponseEntity remove(
            @PathVariable("name") @NotBlank String name
    ) {
        boxService.remove(name);
        return ResponseEntity.ok().build();
    }
}
