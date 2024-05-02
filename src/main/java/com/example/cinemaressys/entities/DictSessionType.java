package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class DictSessionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dictSessionId;
    private String name;

    public DictSessionType(String name) {
        this.name = name;
    }

    public DictSessionType() {
    }
}

