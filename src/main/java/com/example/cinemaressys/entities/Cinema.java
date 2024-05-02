package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cinemaId;

    private String name;
    private String adress;

    public Cinema() {
    }

    public Cinema(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }
}
