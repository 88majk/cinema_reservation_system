package com.example.cinemaressys.services.seat;

import com.example.cinemaressys.dtos.seat.ListSeatRequestDto;
import com.example.cinemaressys.dtos.seat.SeatsResponseDto;

public interface SeatService {
    void createSeatsInNewHall(ListSeatRequestDto listSeatRequestDto);
    SeatsResponseDto getSeatsByMovieSessionId(int movieSessionId, int bookingId);
}
