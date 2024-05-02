package com.example.cinemaressys.services;

import com.example.cinemaressys.dtos.cinema.CinemaRequestDto;
import com.example.cinemaressys.dtos.cinema.CinemaResponseDto;
import com.example.cinemaressys.entities.Cinema;

import java.util.List;
import java.util.Optional;

public interface CinemaService {
    void addCinema(CinemaRequestDto requestDto);
    List<CinemaResponseDto> getAllCinemas();
    Optional<Cinema> getCinema(int id);
    void updateCinema(int cinemaId, CinemaRequestDto requestDto);
    void deleteCinema(int cinemaId);

}
