package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class DictSeatClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dictSeatClassId;
    private String name;

    public DictSeatClass(String name) {
        this.name = name;
    }

    public DictSeatClass() {
    }
}
