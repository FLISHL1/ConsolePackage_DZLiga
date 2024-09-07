package ru.liga;

import java.util.ArrayList;
import java.util.Objects;

public class Box {
    /**
     * Фиктивный объем
     * То есть объем фигуры + часть объема до прямоугольника
     */
    private int volume;
    /**
     * Ширина фигуры + недостоющяя ширина до прямоугольника
     */
    private int width;
    /**
     * Ширина фигуры + недостоющяя ширина до прямоугольника
     */
    private int length;

    /**
     * Представление фигуры в виде двумерного массива
     * */
    private ArrayList<ArrayList<Integer>> space;

    public Box(ArrayList<ArrayList<Integer>> space) {
        this.space = space;
        length = space.size();
        width = space.stream()
                .max((line1, line2) -> Integer.compare(line1.size(), line2.size()))
                .get()
                .size();
        volume = length * width;
    }

    public ArrayList<ArrayList<Integer>> getSpace() {
        return space;
    }

    public void setSpace(ArrayList<ArrayList<Integer>> space) {
        this.space = space;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return width == box.width && length == box.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, length);
    }

    @Override
    public String toString() {
        return "Box{" +
                "width=" + width +
                ", length=" + length +
                ", space=" + space +
                '}';
    }
}

