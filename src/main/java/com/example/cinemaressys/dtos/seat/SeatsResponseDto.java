package com.example.cinemaressys.dtos.seat;

import com.example.cinemaressys.entities.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SeatsResponseDto {
    private int cinemaHallId;
    private String cinemaHallName;
    private List<RowDto> rows;
}
