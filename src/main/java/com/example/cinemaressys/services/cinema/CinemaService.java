package com.example.cinemaressys.services.cinema;

import com.example.cinemaressys.dtos.cinema.CinemaRequestDto;
import com.example.cinemaressys.dtos.cinema.CinemaResponseDto;

import java.util.List;

public interface CinemaService {
    void addCinema(CinemaRequestDto requestDto);
    List<CinemaResponseDto> getAllCinemas();
    CinemaResponseDto getCinema(int id);
    void updateCinema(int cinemaId, CinemaRequestDto requestDto);
    Boolean deleteCinema(int cinemaId);
}
