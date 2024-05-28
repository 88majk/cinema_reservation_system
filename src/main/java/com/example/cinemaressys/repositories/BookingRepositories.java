package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.Booking;
import com.example.cinemaressys.entities.BookingSeat;
import com.example.cinemaressys.entities.MovieSession;
import com.example.cinemaressys.entities.Seat;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    @Query("SELECT bs FROM BookingSeat  bs WHERE bs.booking.bookingId = :bookingId")
    List<BookingSeat> getBookingDetailsByBookingId(@Param("bookingId") int bookingId);
    @Query("SELECT bs.movieSession FROM BookingSeat bs WHERE bs.booking.bookingId = :bookingId")
    MovieSession getMovieSessionByBookingId(@Param("bookingId") int bookingId);
    @Query("SELECT bs.seat FROM BookingSeat bs WHERE bs.booking.bookingId = :bookingId")
    List<Seat> getSeatsFromOrder(@Param("bookingId") int bookingId);

    @Query("SELECT COUNT(bs) > 0 FROM Booking bs WHERE bs.bookingId = :bookingId")
    boolean existsByBookingId(@Param("bookingId") int bookingId);

    @Transactional
    @Modifying
    @Query("UPDATE Booking bs SET bs.dictBookingStatus.id = :statusId WHERE bs.bookingId = :bookingId")
    void updateStatusByBookingId(@Param("bookingId") int bookingId, @Param("statusId") int statusId);
}
