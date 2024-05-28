package com.example.cinemaressys.dtos.moviesession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MovieSessionInfoResponse {
    private String movieName;
    private String cinemaLocalization;
    private String cinemaName;
    private String cinemaHallName;
    private String dateOfSession;
    private String timeOfSession;
    private String movieSessionTypeName;
    private boolean isSubtitles;
}
