package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.BookingSeat;
import com.example.cinemaressys.entities.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingSeatRepositories extends JpaRepository<BookingSeat, Integer> {
    @Query("SELECT bs FROM BookingSeat bs WHERE bs.movieSession.movieSessionId = :movieSessionId AND bs.dictBookingStatus.id IN :statusIds")
    List<BookingSeat> getByMovieSessionMovieSessionIdAndDictBookingStatusIdIn(@Param("movieSessionId") int movieSessionId, @Param("statusIds") List<Integer> statusIds);

    List<BookingSeat> findByBookingBookingIdAndMovieSessionMovieSessionId(int bookingId, int movieSessionId);
}
