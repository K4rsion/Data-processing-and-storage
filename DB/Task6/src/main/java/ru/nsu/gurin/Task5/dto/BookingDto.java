package ru.nsu.gurin.Task5.dto;


import java.math.BigDecimal;

public class BookingDto {
    private Integer flightId;
    private String bookRef;
    private String ticketNo;
    private BigDecimal amount;
    private String flightNo;
    private String name;
    private String doc;
    private String fareConditions;

    public BookingDto(Integer flightId, String bookRef, String ticketNo, BigDecimal amount, String flightNo, String name, String doc, String fareConditions) {
        this.flightId = flightId;
        this.bookRef = bookRef;
        this.ticketNo = ticketNo;
        this.amount = amount;
        this.flightNo = flightNo;
        this.name = name;
        this.doc = doc;
        this.fareConditions = fareConditions;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getBookRef() {
        return bookRef;
    }

    public void setBookRef(String bookRef) {
        this.bookRef = bookRef;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public String getName() {
        return name;
    }

    public String getDoc() {
        return doc;
    }

    public String getFareConditions() {
        return fareConditions;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public void setFareConditions(String fareConditions) {
        this.fareConditions = fareConditions;
    }
}
