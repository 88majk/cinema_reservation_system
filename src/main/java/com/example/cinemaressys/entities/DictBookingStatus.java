package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class DictBookingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dictBookingStatusId;
    private String name;

    public DictBookingStatus(String name) {
        this.name = name;
    }

    public DictBookingStatus() {
    }
}
