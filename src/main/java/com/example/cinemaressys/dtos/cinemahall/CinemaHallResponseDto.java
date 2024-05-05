package com.example.cinemaressys.dtos.cinemahall;

import com.example.cinemaressys.entities.Cinema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CinemaHallResponseDto {
    private int cinemaHallId;
    private String name;
    private Cinema cinema;
}
