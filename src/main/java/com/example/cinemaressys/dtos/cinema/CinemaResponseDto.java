package com.example.cinemaressys.dtos.cinema;

import com.example.cinemaressys.entities.CinemaHall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CinemaResponseDto {
    private int id;
    private String address;
    private String name;
    private String localization;
    private String phoneNumber;
    private String emailContact;
    private Set<String> halls;
}
