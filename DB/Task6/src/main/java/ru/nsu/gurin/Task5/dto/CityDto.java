package ru.nsu.gurin.Task5.dto;

public class CityDto {
    private String fromCity;
    private String toCity;

    public CityDto(String fromCity, String toCity) {
        this.fromCity = fromCity;
        this.toCity = toCity;
    }

    // Getters and Setters
    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }
}
