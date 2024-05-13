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
//    @Query("SELECT ms FROM MovieSession ms " +
//            "WHERE ms.cinema_hall_id  = :cinema_hall_id " +
//            "AND ms.date_of_session  >= :date_of_session " +
//            "AND cast(ms.time_of_session  as time) >= cast(:time_of_session as time)")

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


}
