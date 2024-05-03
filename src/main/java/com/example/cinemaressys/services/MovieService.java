package com.example.cinemaressys.services;

import com.example.cinemaressys.dtos.movie.MovieRequestDto;
import com.example.cinemaressys.dtos.movie.MovieResponseDto;

import java.util.List;

public interface MovieService {
    void addMovie(MovieRequestDto requestDto);
    List<MovieResponseDto> getAllMovies();
    MovieResponseDto getMovie(int movieId);
    void updateMovie(int movieId, MovieRequestDto requestDto);
    Boolean deleteMovie(int movieId);
}
