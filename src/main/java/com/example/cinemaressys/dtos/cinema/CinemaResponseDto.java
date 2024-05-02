package com.example.cinemaressys.dtos.cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
