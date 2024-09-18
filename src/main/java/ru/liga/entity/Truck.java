package ru.liga.entity;



public class Truck {
    private final int BORDER_WIDTH = 1;
    private final char BORDER_CHAR = '+';
    private final TruckTrunk trunk = new TruckTrunk();

    public TruckTrunk getTrunk() {
        return trunk;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int line = trunk.getSpace().length-1; line >= 0; line--) {
            stringBuilder.append(BORDER_CHAR);
            for (Integer column: trunk.getSpace()[line]){
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
