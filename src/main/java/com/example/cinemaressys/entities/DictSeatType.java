package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class DictSeatType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dictSeatTypeId;
    private String name;

    public DictSeatType(String name) {
        this.name = name;
    }

    public DictSeatType() {
    }
}
