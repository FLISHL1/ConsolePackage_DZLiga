package ru.liga.mapper;

import org.springframework.stereotype.Component;
import ru.liga.dto.BoxDtoRequest;
import ru.liga.dto.BoxDtoResponse;
import ru.liga.entity.Box;
import ru.liga.service.box.BoxService;

import java.util.ArrayList;
import java.util.List;

@Component
public class DtoBoxMapper {
    public Box dtoBoxRequestToBox(BoxDtoRequest boxDtoRequest) {
        if (boxDtoRequest == null) {
            return null;
        }
        Box box = new Box();
        box.setName(boxDtoRequest.getName());
        box.setSpace(boxDtoRequest.getSpace());
        changeCharSpace(boxDtoRequest, box);
        return box;
    }

    private void changeCharSpace(BoxDtoRequest boxDtoRequest, Box box) {
        if (boxDtoRequest.getCharSpace() == null & boxDtoRequest.getCharSpace().isBlank()) {
            box.changeCharSpace(boxDtoRequest.getCharSpace());
        }
    }

    public Box dtoBoxRequestToBox(BoxDtoRequest boxDtoRequest, Box box) {
        if (boxDtoRequest == null) {
            return null;
        }
        if (boxDtoRequest.getName() != null) {
            box.setName(boxDtoRequest.getName());
        }
        if (boxDtoRequest.getSpace() != null) {
            box.setSpace(boxDtoRequest.getSpace());
        }

        changeCharSpace(boxDtoRequest, box);
        return box;
    }

    public BoxDtoResponse boxToDtoBoxResponse(Box box) {
        if (box == null) {
            return null;
        }
        return new BoxDtoResponse(box.getId(), box.getName(), box.getSpace());
    }


    public List<BoxDtoResponse> boxToDtoBoxResponse(List<Box> boxes) {
        if (boxes == null) {
            return null;
        }
        List<BoxDtoResponse> boxDtoList = new ArrayList<>();
        for (Box box : boxes) {
            boxDtoList.add(new BoxDtoResponse(box.getId(), box.getName(), box.getSpace()));

        }
        return boxDtoList;
    }
}
