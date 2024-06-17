package ru.nsu.gurin.Task5.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "flight_details")
public class FlightDetails {

    @Id
    @Column(name = "flight_id")
    private Integer flightId;

    @Column(name = "flight_no", length = 6)
    private String flightNo;

    @Column(name = "departure_city")
    private String departureCity;

    @Column(name = "departure_airport", length = 3)
    private String departureAirport;

    @Column(name = "arrival_city")
    private String arrivalCity;

    @Column(name = "arrival_airport", length = 3)
    private String arrivalAirport;

    @Column(name = "scheduled_departure")
    private String scheduledDeparture;

    @Column(name = "scheduled_arrival")
    private String scheduledArrival;


    @ElementCollection
//    @CollectionTable(name = "booking_flight_details", joinColumns = @JoinColumn(name = "flight_details_id"))
//    @Column(name = "days_of_week")
    private List<Integer> daysOfWeek;

    @Column(name = "fare_conditions", length = 10)
    private String fareConditions;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    // Getters and Setters

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

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getScheduledDeparture() {
        return scheduledDeparture;
    }

    public void setScheduledDeparture(String scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public String getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(String scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public List<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<Integer> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getFareConditions() {
        return fareConditions;
    }

    public void setFareConditions(String fareConditions) {
        this.fareConditions = fareConditions;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
