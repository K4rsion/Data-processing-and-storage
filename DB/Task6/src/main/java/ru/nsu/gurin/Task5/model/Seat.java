package ru.nsu.gurin.Task5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @Column(name = "aircraft_code", nullable = false, length = 3)
    private String aircraftCode;

    @Id
    @Column(name = "seat_no", nullable = false, length = 4)
    private String seatNo;

    @Column(name = "fare_conditions", nullable = false, length = 10)
    private String fareConditions;

    // Getters and setters
    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getFareConditions() {
        return fareConditions;
    }

    public void setFareConditions(String fareConditions) {
        this.fareConditions = fareConditions;
    }
}
