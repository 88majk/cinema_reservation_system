package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.BookingSeat;
import com.example.cinemaressys.entities.MovieSession;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingSeatRepositories extends JpaRepository<BookingSeat, Integer> {
    @Query("SELECT bs FROM BookingSeat bs WHERE bs.movieSession.movieSessionId = :movieSessionId AND bs.dictBookingStatus.id IN :statusIds")
    List<BookingSeat> getByMovieSessionMovieSessionIdAndDictBookingStatusIdIn(@Param("movieSessionId") int movieSessionId, @Param("statusIds") List<Integer> statusIds);

    List<BookingSeat> findByBookingBookingIdAndMovieSessionMovieSessionId(int bookingId, int movieSessionId);

    @Transactional
    @Modifying
    @Query("UPDATE BookingSeat bs SET bs.dictBookingStatus.id = :statusId WHERE bs.booking.bookingId = :bookingId")
    void updateStatusByBookingId(@Param("bookingId") int bookingId, @Param("statusId") int statusId);

    @Query("SELECT COUNT(bs) > 0 FROM BookingSeat bs WHERE bs.booking.bookingId = :bookingId")
    boolean existsByBookingId(@Param("bookingId") int bookingId);

    @Transactional
    @Modifying
    @Query("DELETE FROM BookingSeat bs WHERE bs.booking.bookingId = :bookingId")
    void deleteByBookingBookingId(@Param("bookingId") int bookingId);
}
