package com.example.cinemaressys.dtos.cinemahall;

import com.example.cinemaressys.entities.Cinema;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CinemaHallRequestDto {
    private String name;
    @JsonIgnore
    private Cinema cinema;
}
