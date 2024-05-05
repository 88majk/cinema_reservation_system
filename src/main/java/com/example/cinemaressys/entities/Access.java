package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table
@Data
public class Access {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accessId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;


    public Access(User user, Cinema cinema) {
        this.user = user;
        this.cinema = cinema;
    }

    public Access() {
    }
}
