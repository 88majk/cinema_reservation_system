package com.example.cinemaressys.dtos.seat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SeatDto {
    private char row;
    private int columns;
    private String seatClass;
}
