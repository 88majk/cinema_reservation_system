package com.example.cinemaressys.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cinemaHallId;
    private String name;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    @JsonIgnore
    private Cinema cinema;


    public CinemaHall(Cinema cinema, String name) {
        this.cinema= cinema;
        this.name = name;
    }

    public CinemaHall() {
    }
}
