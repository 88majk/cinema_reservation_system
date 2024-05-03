package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.Genre;
import com.example.cinemaressys.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface MovieRepositories extends JpaRepository<Movie, Integer> {
    @Query("SELECT m.movieGenres FROM Movie m WHERE m.movieId = :movieId")
    Set<Genre> findGenresByMovieId(@Param("movieId") int movieId);
}
