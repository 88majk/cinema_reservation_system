package com.example.cinemaressys.dtos.cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaRequestDto {
    private String address;
    private String name;
    private String localization;
    private String phoneNumber;
    private String emailContact;
}
