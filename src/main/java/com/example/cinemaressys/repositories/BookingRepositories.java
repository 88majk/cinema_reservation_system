package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.Booking;
import com.example.cinemaressys.entities.BookingSeat;
import com.example.cinemaressys.entities.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepositories extends JpaRepository<Booking, Integer> {
    Booking findByBookingId(int bookingId);
    @Query("SELECT MAX(b.bookingNumber) FROM Booking b")
    Integer findMaxBookingNumber();
    Booking findByBookingNumber(int bookingNumber);
    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId")
    List<Booking> getBookingsByUserId(@Param("userId") int userId);
    @Query("SELECT bs.movieSession FROM BookingSeat bs WHERE bs.booking.bookingId = :bookingId")
    MovieSession getMovieSessionByBookingId(@Param("bookingId") int bookingId);

}
