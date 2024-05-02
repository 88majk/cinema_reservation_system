package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
@Data
public class MovieSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieSessionId;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movieId;

    @ManyToOne
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHall cinemaHall;

    @ManyToOne
    @JoinColumn(name = "dict_session_type_id")
    private DictSessionType dictSessionType;

    private LocalDate dateOfSession;
    private LocalTime timeOfSession;
    private boolean isSubtitles;

    public MovieSession(Movie movieId, CinemaHall cinemaHall, DictSessionType dictSessionType,
                        LocalDate dateOfSession, LocalTime timeOfSession, boolean isSubtitles) {
        this.movieId = movieId;
        this.cinemaHall = cinemaHall;
        this.dictSessionType = dictSessionType;
        this.dateOfSession = dateOfSession;
        this.timeOfSession = timeOfSession;
        this.isSubtitles = isSubtitles;
    }

    public MovieSession(){
    }
}
