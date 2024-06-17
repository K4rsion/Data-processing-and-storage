package ru.nsu.gurin.Task5.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import jakarta.persistence.Id;

@Entity
@Table(name = "flights_v")
public class FlightView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Integer flightId;

    @Column(name = "flight_no", length = 6)
    private String flightNo;

    @Column(name = "scheduled_departure")
    private ZonedDateTime scheduledDeparture;

    @Column(name = "scheduled_departure_local")
    private LocalDateTime scheduledDepartureLocal;

    @Column(name = "scheduled_arrival")
    private ZonedDateTime scheduledArrival;

    @Column(name = "scheduled_arrival_local")
    private LocalDateTime scheduledArrivalLocal;

    @Column(name = "scheduled_duration")
    private Duration scheduledDuration;

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

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "aircraft_code", length = 3)
    private String aircraftCode;

    @Column(name = "actual_departure")
    private ZonedDateTime actualDeparture;

    @Column(name = "actual_departure_local")
    private LocalDateTime actualDepartureLocal;

    @Column(name = "actual_arrival")
    private ZonedDateTime actualArrival;

    @Column(name = "actual_arrival_local")
    private LocalDateTime actualArrivalLocal;

    @Column(name = "actual_duration")
    private Duration actualDuration;

    // Getters and setters

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public ZonedDateTime getScheduledDeparture() {
        return scheduledDeparture;
    }

    public void setScheduledDeparture(ZonedDateTime scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public LocalDateTime getScheduledDepartureLocal() {
        return scheduledDepartureLocal;
    }

    public void setScheduledDepartureLocal(LocalDateTime scheduledDepartureLocal) {
        this.scheduledDepartureLocal = scheduledDepartureLocal;
    }

    public ZonedDateTime getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(ZonedDateTime scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public LocalDateTime getScheduledArrivalLocal() {
        return scheduledArrivalLocal;
    }

    public void setScheduledArrivalLocal(LocalDateTime scheduledArrivalLocal) {
        this.scheduledArrivalLocal = scheduledArrivalLocal;
    }

    public Duration getScheduledDuration() {
        return scheduledDuration;
    }

    public void setScheduledDuration(Duration scheduledDuration) {
        this.scheduledDuration = scheduledDuration;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }

    public ZonedDateTime getActualDeparture() {
        return actualDeparture;
    }

    public void setActualDeparture(ZonedDateTime actualDeparture) {
        this.actualDeparture = actualDeparture;
    }

    public LocalDateTime getActualDepartureLocal() {
        return actualDepartureLocal;
    }

    public void setActualDepartureLocal(LocalDateTime actualDepartureLocal) {
        this.actualDepartureLocal = actualDepartureLocal;
    }

    public ZonedDateTime getActualArrival() {
        return actualArrival;
    }

    public void setActualArrival(ZonedDateTime actualArrival) {
        this.actualArrival = actualArrival;
    }

    public LocalDateTime getActualArrivalLocal() {
        return actualArrivalLocal;
    }

    public void setActualArrivalLocal(LocalDateTime actualArrivalLocal) {
        this.actualArrivalLocal = actualArrivalLocal;
    }

    public Duration getActualDuration() {
        return actualDuration;
    }

    public void setActualDuration(Duration actualDuration) {
        this.actualDuration = actualDuration;
    }
}
