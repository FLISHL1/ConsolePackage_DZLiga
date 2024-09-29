package ru.liga.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Truck {
    @JsonIgnore
    private final int BORDER_WIDTH = 1;
    @JsonIgnore
    private final char BORDER_CHAR = '+';
    private final Trunk trunk = new Trunk();

    public Trunk getTrunk() {
        return trunk;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int line = trunk.getSpace().length-1; line >= 0; line--) {
            stringBuilder.append(BORDER_CHAR);
            for (String column: trunk.getSpace()[line]){
                if (column == null)
                    stringBuilder.append(" ");
                else
                    stringBuilder.append(column);
            }
            stringBuilder.append(BORDER_CHAR).append("\n");
        }
        stringBuilder.append(Character
                .toString(BORDER_CHAR)
                .repeat(trunk.WIDTH + BORDER_WIDTH + BORDER_WIDTH)
        );
        return stringBuilder.toString();
    }
}
