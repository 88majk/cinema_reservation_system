package com.example.cinemaressys.dtos.seat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ListSeatRequestDto {
    private int cinemaHallId;
    private List<SeatDto> seats;

}
