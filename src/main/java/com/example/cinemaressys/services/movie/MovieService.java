package com.example.cinemaressys.services.movie;

import com.example.cinemaressys.dtos.movie.MovieRequestDto;
import com.example.cinemaressys.dtos.movie.MovieResponseDto;
import com.example.cinemaressys.entities.Movie;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    void addMovie(MovieRequestDto requestDto);
    List<MovieResponseDto> getAllMovies();
    MovieResponseDto getMovie(int movieId);
    void updateMovie(int movieId, MovieRequestDto requestDto);
    Boolean deleteMovie(int movieId);
    public List<Movie> getMoviesFromApi() throws IOException;
}
