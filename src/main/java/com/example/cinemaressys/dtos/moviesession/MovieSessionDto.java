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
    private boolean isSubtitles;
}
