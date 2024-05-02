package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class BookingSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingSeatId;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "movie_session_id")
    private MovieSession movieSession;

    @ManyToOne
    @JoinColumn(name = "price_id")
    private Price price;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "dict_booking_status_id")
    private DictBookingStatus dictBookingStatus;

    public BookingSeat(Booking booking, MovieSession movieSession, Price price, Seat seat, DictBookingStatus dictBookingStatus) {
        this.booking = booking;
        this.movieSession = movieSession;
        this.price = price;
        this.seat = seat;
        this.dictBookingStatus = dictBookingStatus;
    }

    public BookingSeat() {
    }
}
