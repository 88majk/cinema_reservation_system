package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cinemaHallId;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
    private String name;

    public CinemaHall(Cinema cinema, String name) {
        this.cinema= cinema;
        this.name = name;
    }

    public CinemaHall() {
    }
}
