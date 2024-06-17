package ru.nsu.gurin.Task5.dto;

public class CheckInDto {
    private String flightNo;
    private Integer flightId;
    private String ticketNo;
    private String seatNo;

    public CheckInDto(Integer flightId, String ticketNo, String seatNo, String flightNo) {
        this.flightNo = flightNo;
        this.flightId = flightId;
        this.ticketNo = ticketNo;
        this.seatNo = seatNo;
    }

    // Геттеры и сеттеры
    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    @Override
    public String toString() {
        return "CheckInDto{" +
                "flightId=" + flightId +
                ", ticketNo='" + ticketNo + '\'' +
                ", seatNo='" + seatNo + '\'' +
                '}';
    }
}
