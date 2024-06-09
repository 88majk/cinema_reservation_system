package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.CinemaHall;
import com.example.cinemaressys.entities.Movie;
import com.example.cinemaressys.entities.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface MovieSessionRepositories extends JpaRepository<MovieSession, Integer> {

    @Query("SELECT ms FROM MovieSession ms " +
            "WHERE ms.cinemaHall = :cinemaHall " +
            "AND ms.dateOfSession = :dateOfSession " +
            "AND cast(ms.timeOfSession as time) >= cast(:timeOfSession as time) " +
            "ORDER BY ms.timeOfSession ASC")
    List<MovieSession> getMovieSessionByCinemaHallAndDateOfSessionAndTimeOfSession(
            @Param("cinemaHall") CinemaHall cinemaHall,
            @Param("dateOfSession") LocalDate dateOfSession,
            @Param("timeOfSession") LocalTime timeOfSession);

    MovieSession findByMovieSessionId(int movieSessionId);

    @Query("SELECT COUNT(bs) FROM BookingSeat bs WHERE bs.movieSession.movieSessionId = :sessionId")
    Integer countBookedSeatsForSession(@Param("sessionId") int sessionId);

    @Query("SELECT COUNT(s) FROM Seat s WHERE s.cinemaHall.cinemaHallId = :cinemaHallId")
    Integer countAllSeatsForCinemaHall(@Param("cinemaHallId") int cinemaHallId);
}
