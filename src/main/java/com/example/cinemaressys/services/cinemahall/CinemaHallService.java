package com.example.cinemaressys.services.cinemahall;

import com.example.cinemaressys.dtos.cinemahall.CinemaHallRequestDto;
import com.example.cinemaressys.dtos.cinemahall.CinemaHallResponseDto;

import java.util.List;

public interface CinemaHallService {
    void addHallToCinema(int cinemaId, CinemaHallRequestDto requestDto);
    Boolean deleteHallFromCinema(int hallId);
    Boolean deleteAllHalls(int cinemaId);
    List<CinemaHallResponseDto> getHallsFromCinema(int cinemaId);
}
