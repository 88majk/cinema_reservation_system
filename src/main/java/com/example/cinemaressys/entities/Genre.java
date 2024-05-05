package com.example.cinemaressys.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table
@Data
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;
    private String name;

    @ManyToMany(mappedBy = "movieGenres")
    @JsonIgnore
    private Set<Movie> movies = new HashSet<>();

    public Genre(){
    }

    public Genre(String name){
        this.name = name;
    }


}
