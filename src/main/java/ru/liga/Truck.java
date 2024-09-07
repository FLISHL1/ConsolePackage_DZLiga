package ru.liga;


public class Truck {
    private static final int HEIGHT = 6;
    private static final int WIDTH = 6;
    private static final char BORDER_CHAR = '+';

    private final Integer[][] cargoSpace = new Integer[HEIGHT][WIDTH];

    public Integer[][] getCargoSpace() {
        return cargoSpace;
    }

    public int getHeight(){
        return HEIGHT;
    }

    public int getWidth(){
        return WIDTH;
    }
    /**
     * +      +
     * +^     +
     * +|     +
     * +y     +
     * +x->   +
     * ++++++++
     *
     *
     * @param box
     * @param x
     * @param y
     */
    public void addBox(Box box, int x, int y){
        y = HEIGHT - y - 1;
        var boxSpace = box.getSpace();

        for (int line = boxSpace.size()-1; line >= 0; line--) {
            int xIter = x;
            for (int column = 0; column < boxSpace.get(line).size(); column++) {
                if (cargoSpace[y][xIter] != null)
                    throw new RuntimeException();
                cargoSpace[y][xIter++] = boxSpace.get(line).get(column);
            }
            y--;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer[] line : cargoSpace) {
            stringBuilder.append(BORDER_CHAR);
            for (Integer column: line){
                if (column == null)
                    stringBuilder.append(" ");
                else
                    stringBuilder.append(column);
            }
            stringBuilder.append(BORDER_CHAR).append("\n");
        }
        stringBuilder.append(Character.toString(BORDER_CHAR).repeat(WIDTH+2));
        return stringBuilder.toString();
    }
}
