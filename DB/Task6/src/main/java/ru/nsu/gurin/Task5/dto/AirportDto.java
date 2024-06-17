package ru.nsu.gurin.Task5.dto;

public class AirportDto {
    private String fromAirport;
    private String toAirport;

    public AirportDto(String fromAirport, String fromAirportName, String toAirport, String toAirportName) {
        this.fromAirport = fromAirportName + "(" + fromAirport + ")";
        this.toAirport = toAirportName + "(" + toAirport + ")";
    }

    // Getters and Setters
    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }
}
