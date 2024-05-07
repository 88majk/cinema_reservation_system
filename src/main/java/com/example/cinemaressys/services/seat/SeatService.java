package com.example.cinemaressys.services.seat;

import com.example.cinemaressys.dtos.seat.ListSeatRequestDto;

public interface SeatService {
    void createSeatsInNewHall(ListSeatRequestDto listSeatRequestDto);
}
