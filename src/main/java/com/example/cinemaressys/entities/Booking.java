package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "dict_booking_status_id")
    private DictBookingStatus dictBookingStatus;

    private int bookingNumber;
    private float totalPrice;

    public Booking(User user, DictBookingStatus dictBookingStatus, int bookingNumber, float totalPrice) {
        this.user = user;
        this.dictBookingStatus = dictBookingStatus;
        this.bookingNumber = bookingNumber;
        this.totalPrice = totalPrice;
    }

    public Booking() {
    }
}
