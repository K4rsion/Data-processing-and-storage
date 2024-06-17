package ru.nsu.gurin.Task5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @Column(name = "book_ref", nullable = false, length = 6)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String bookRef;

    @Column(name = "book_date", nullable = false)
    private ZonedDateTime bookDate;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    public String getBookRef() {
        return bookRef;
    }

    public void setBookRef(String bookRef) {
        this.bookRef = bookRef;
    }

    public ZonedDateTime getBookDate() {
        return bookDate;
    }

    public void setBookDate(ZonedDateTime bookDate) {
        this.bookDate = bookDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

}
