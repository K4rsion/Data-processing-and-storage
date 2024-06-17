package ru.nsu.gurin.Task5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Duration;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @Column(name = "flight_no", length = 6)
    private String flightNo;

    @Column(name = "departure_airport", length = 3)
    private String departureAirport;

    @Column(name = "departure_airport_name")
    private String departureAirportName;

    @Column(name = "departure_city")
    private String departureCity;

    @Column(name = "arrival_airport", length = 3)
    private String arrivalAirport;

    @Column(name = "arrival_airport_name")
    private String arrivalAirportName;

    @Column(name = "arrival_city")
    private String arrivalCity;

    @Column(name = "aircraft_code", length = 3)
    private String aircraftCode;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "days_of_week")
    private int[] daysOfWeek;

    // Getters and setters

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int[] getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(int[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
}