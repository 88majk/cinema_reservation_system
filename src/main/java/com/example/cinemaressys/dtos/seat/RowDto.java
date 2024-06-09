package com.example.cinemaressys.dtos.seat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RowDto {
    private String rowName;
    private List<SeatDto> seats;
}
