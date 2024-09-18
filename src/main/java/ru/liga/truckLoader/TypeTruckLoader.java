package ru.liga.truckLoader;

public enum TypeTruckLoader {
    UNIFORM ("1"),
    MAXIMAL ("2");

    private final String number;
    TypeTruckLoader(String str){
        number = str;
    }

    @Override
    public String toString() {
        return number;
    }
}
