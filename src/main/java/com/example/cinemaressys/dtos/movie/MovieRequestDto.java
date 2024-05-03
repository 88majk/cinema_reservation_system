package com.example.cinemaressys.dtos.movie;

import com.example.cinemaressys.entities.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MovieRequestDto {
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int minimumAge;
    private int duration;
    private String productionCountry;
    private String director;
    private Set<Genre> movieGenres;
}
