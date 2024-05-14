package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.Booking;
import com.example.cinemaressys.entities.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepositories extends JpaRepository<Booking, Integer> {
    Booking findByBookingId(int bookingId);
    @Query("SELECT MAX(b.bookingNumber) FROM Booking b")
    Integer findMaxBookingNumber();
    Booking findByBookingNumber(int bookingNumber);

}
