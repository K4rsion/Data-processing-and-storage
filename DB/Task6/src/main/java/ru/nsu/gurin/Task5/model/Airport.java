package ru.nsu.gurin.Task5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.awt.geom.Point2D;

@Entity
@Table(name = "airports")
public class Airport {

    @Id
    @Column(name = "airport_code")
    private String airportCode;

    @Column(name = "airport_name")
    private String airportName;

    @Column(name = "city")
    private String city;

    // Point2D.Double can be used to represent coordinates (x, y)
    @Column(name = "coordinates")
    private Point2D.Double coordinates;

    @Column(name = "timezone")
    private String timezone;

    // Getters and Setters

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Point2D.Double getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point2D.Double coordinates) {
        this.coordinates = coordinates;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
