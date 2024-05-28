package com.example.cinemaressys.dtos.moviesession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MovieSessionDto {
    private int movieSessionId;
    private LocalDate dateOfSession;
    private LocalTime timeOfSession;
    private String sessionTypeName;
    private String hallName;
    private int availableSeat;
    private int allSeats;
    private boolean isSubtitles;
}
