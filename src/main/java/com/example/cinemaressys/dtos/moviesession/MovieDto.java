package com.example.cinemaressys.dtos.moviesession;

import com.example.cinemaressys.entities.Genre;
import com.example.cinemaressys.entities.MovieSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MovieDto {
    private int movieId;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int minimumAge;
    private int duration;
    private String productionCountry;
    private String director;
    private List<MovieSessionDto> movieSessionList;
    private Set<Genre> genres;
}
