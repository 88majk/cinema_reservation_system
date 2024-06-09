package com.example.cinemaressys.services.moviesession;

import com.example.cinemaressys.dtos.moviesession.MovieDto;
import com.example.cinemaressys.dtos.moviesession.MovieSessionDto;
import com.example.cinemaressys.dtos.moviesession.MovieSessionInfoResponse;
import com.example.cinemaressys.dtos.moviesession.MovieSessionResponse;
import com.example.cinemaressys.entities.CinemaHall;
import com.example.cinemaressys.entities.Genre;
import com.example.cinemaressys.entities.Movie;
import com.example.cinemaressys.entities.MovieSession;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.repositories.CinemaHallRepositories;
import com.example.cinemaressys.repositories.MovieRepositories;
import com.example.cinemaressys.repositories.MovieSessionRepositories;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;



@Service
@AllArgsConstructor
public class MovieSessionServiceImpl implements MovieSessionService {
    final private MovieSessionRepositories movieSessionRepositories;
    final private MovieRepositories movieRepositories;
    final private CinemaHallRepositories cinemaHallRepositories;

    @Override
    public MovieSessionResponse getMoviesSessions(int cinemaId, String movieSessionDateStr) {
        try {
            LocalDate movieSessionDate = LocalDate.parse(movieSessionDateStr);
            LocalTime localTime = movieSessionDate.equals(LocalDate.now()) ? LocalTime.now().minusHours(1) : LocalTime.parse("00:00");

            List<CinemaHall> cinemaHalls = cinemaHallRepositories.getAllHallsByCinemaId(cinemaId);

            List<MovieSession> allMovieSession = cinemaHalls.stream()
                    .flatMap(cinemaHall -> {
                        try {
                            return movieSessionRepositories.
                                    getMovieSessionByCinemaHallAndDateOfSessionAndTimeOfSession(cinemaHall,
                                            movieSessionDate, localTime).stream();
                        } catch (Exception e) {
                            throw new MyException("Failed to fetch movie sessions for cinema hall: " +
                                    cinemaHall.getCinemaHallId() + " " + e);
                        }
                    })
                    .collect(Collectors.toList());

            Map<Movie, List<MovieSession>> movieSessionsMap = allMovieSession.stream()
                    .collect(Collectors.groupingBy(MovieSession::getMovieId));

            List<MovieDto> moviesWithSessions = movieSessionsMap.entrySet().stream()
                    .map(entry -> {
                        Movie movie = entry.getKey();
                        List<MovieSession> sessions = entry.getValue();
                        List<MovieSessionDto> movieSessionDtoList = sessions.stream()
                                .sorted(Comparator.comparing(session -> session.getTimeOfSession())) // Sortowanie po godzinie
                                .map(session -> new MovieSessionDto(
                                        session.getMovieSessionId(),
                                        session.getDateOfSession(),
                                        session.getTimeOfSession(),
                                        session.getDictSessionType().getName(),
                                        session.getCinemaHall().getName(),
                                        movieSessionRepositories.countAllSeatsForCinemaHall
                                                (session.getCinemaHall().getCinemaHallId()) -
                                                movieSessionRepositories.countBookedSeatsForSession
                                                        (session.getMovieSessionId()),
                                        movieSessionRepositories.countAllSeatsForCinemaHall
                                                (session.getCinemaHall().getCinemaHallId()),
                                        session.isSubtitles()
                                ))
                                .collect(Collectors.toList());

                        Set<Genre> genres;
                        try {
                            genres = movieRepositories.findGenresByMovieId(movie.getMovieId());
                        } catch (Exception e) {
                            throw new MyException("Failed to fetch genres for movie: " + movie.getMovieId() + " " + e);
                        }

                        return new MovieDto(
                                movie.getMovieId(),
                                movie.getName(),
                                movie.getDescription(),
                                movie.getReleaseDate(),
                                movie.getMinimumAge(),
                                movie.getDuration(),
                                movie.getProductionCountry(),
                                movie.getDirector(),
                                movieSessionDtoList,
                                genres
                        );
                    })
                    .collect(Collectors.toList());

            return new MovieSessionResponse(moviesWithSessions);
        } catch (Exception e) {
            throw new MyException("Failed to get movies: " + e);
        }
    }

    @Override
    public MovieSessionInfoResponse getMovieSessionInfo(int movieSessionId) {
        MovieSession movieSession = movieSessionRepositories.findByMovieSessionId(movieSessionId);
        if (movieSession == null){
            throw new MyException("MovieSessionId does not exist!");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH);
        String formattedDate = movieSession.getDateOfSession().format(formatter);

        MovieSessionInfoResponse movieSessionInfoResponse = new MovieSessionInfoResponse(
                movieSession.getMovieId().getName(),
                movieSession.getCinemaHall().getCinema().getLocalization(),
                movieSession.getCinemaHall().getCinema().getName(),
                movieSession.getCinemaHall().getName(),
                formattedDate,
                movieSession.getTimeOfSession().toString().substring(0, 5),
                movieSession.getDictSessionType().getName(),
                movieSession.isSubtitles()
        );

        return movieSessionInfoResponse;
    }
}
