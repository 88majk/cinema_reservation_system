package com.example.cinemaressys.dtos.access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AccessCreateAdminRequestDto {
    private String token;
    private String email;
    private boolean allCinema;
    private int cinemaId;
}
