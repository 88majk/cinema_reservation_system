package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int minimumAge;
    private int duration;
    private String productionCountry;
    private String director;

    @ManyToMany
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    public Movie(String name, String description, LocalDate releaseDate, int minimumAge, int duration,
                 String productionCountry, String director, Set<Genre> genres) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.minimumAge = minimumAge;
        this.duration = duration;
        this.productionCountry = productionCountry;
        this.director = director;
        this.genres = genres;
    }

    public Movie(){
    }
}
