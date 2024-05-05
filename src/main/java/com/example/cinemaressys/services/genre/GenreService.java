package com.example.cinemaressys.services.genre;

import com.example.cinemaressys.dtos.genre.GenreResponseDto;

import java.util.List;

public interface GenreService {
    List<GenreResponseDto> getAllGenres();
}
