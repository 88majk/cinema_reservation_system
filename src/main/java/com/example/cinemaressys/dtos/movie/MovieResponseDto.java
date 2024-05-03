package com.example.cinemaressys.dtos.movie;

import com.example.cinemaressys.entities.Genre;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MovieResponseDto {
    private int movieId;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int minimumAge;
    private int duration;
    private String productionCountry;
    private String director;
    private Set<Genre> movieGenres;
}
