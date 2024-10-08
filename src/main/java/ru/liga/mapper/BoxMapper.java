package ru.liga.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.liga.entity.Box;
import ru.liga.util.BoxParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BoxMapper implements RowMapper<Box> {
    private final BoxParser boxParser;

    public BoxMapper(BoxParser boxParser) {
        this.boxParser = boxParser;
    }

    @Override
    public Box mapRow(ResultSet rs, int rowNum) throws SQLException {
        String space = rs.getString("space");
        List<List<String>> spaceList = mapSpaceStringToList(space);
        return new Box(rs.getInt("id"), rs.getString("name"), spaceList);
    }


    /**
     * Преобразует строчку в фигуру коробки
     *
     * @param spaceData Строчка которую можно преобразовать в двумерный массив
     * @return Двумерный массив для фигуры коробки
     */
    public List<List<String>> mapSpaceStringToList(String spaceData) {
        List<List<String>> spaceList = new ArrayList<>();
        for (String line : spaceData.split(" ")) {
            spaceList.add(Arrays.stream(line.split(",")).collect(Collectors.toList()));
        }
        return spaceList;
    }

    /**
     * Преобразует фигуру коробки в строчку
     *
     * @param space фигура короюки
     * @return Строчка с фигурой коробки
     */
    public String mapListSpaceToStringSpace(List<List<String>> space) {
        StringBuilder spaceString = new StringBuilder();
        for (int iterLine = 0; iterLine < space.size(); iterLine++) {
            for (int iterColumn = 0; iterColumn < space.get(iterLine).size(); iterColumn++) {
                spaceString.append(space.get(iterLine).get(iterColumn));
                if (iterColumn != space.get(iterLine).size() - 1) {
                    spaceString.append(",");
                }
            }
            if (iterLine != space.size() - 1) {
                spaceString.append(" ");
            }
        }
        return spaceString.toString();
    }
}
