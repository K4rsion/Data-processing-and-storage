package ru.nsu.gurin.Task5.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlightDto {
    private String departureAirport;
    private String arrivalAirport;
    private String fareConditions;
    private Timestamp[] departuresTime;
    private String[] route;
    private String[] routeNo;
    private BigDecimal[] amounts;

    public FlightDto(String departureAirport, String arrivalAirport, String fareConditions,
                     Timestamp[] departuresTime, String[] route, String[] routeNo, BigDecimal[] amounts) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.fareConditions = fareConditions;
        this.departuresTime = departuresTime;
        this.route = route;
        this.routeNo = routeNo;
        this.amounts = amounts;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getFareConditions() {
        return fareConditions;
    }

    public void setFareConditions(String fareConditions) {
        this.fareConditions = fareConditions;
    }

    public Timestamp[] getDeparturesTime() {
        return departuresTime;
    }

    public void setDeparturesTime(Timestamp[] departuresTime) {
        this.departuresTime = departuresTime;
    }

    public String[] getRoute() {
        return route;
    }

    public void setRoute(String[] route) {
        this.route = route;
    }

    public String[] getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String[] routeNo) {
        this.routeNo = routeNo;
    }

    public BigDecimal[] getAmounts() {
        return amounts;
    }

    public void setAmounts(BigDecimal[] amounts) {
        this.amounts = amounts;
    }

    // Метод для корректировки временных меток
    public void adjustDepartureTimes() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        // Преобразуем Timestamp в LocalDateTime
        List<LocalDateTime> localDateTimes = Arrays.stream(departuresTime)
                .map(Timestamp::toLocalDateTime)
                .collect(Collectors.toList());

        // Пройдемся по списку и увеличим дату, если нужно
        for (int i = 1; i < localDateTimes.size(); i++) {
            if (localDateTimes.get(i).isBefore(localDateTimes.get(i - 1))) {
                localDateTimes.set(i, localDateTimes.get(i).plusDays(1));
            }
        }

        // Преобразуем обратно в Timestamp
        departuresTime = localDateTimes.stream()
                .map(Timestamp::valueOf)
                .toList().toArray(new Timestamp[0]);
    }
}