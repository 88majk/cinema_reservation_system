package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;

    @ManyToOne
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHall cinemaHall;

    @ManyToOne
    @JoinColumn(name = "dict_seat_class_id")
    private DictSeatClass dictSeatClass;

    private char rowNumber;
    private int columnNumber;

    public Seat(CinemaHall cinemaHall, DictSeatClass dictSeatClass, char rowNumber, int columnNumber) {
        this.cinemaHall = cinemaHall;
        this.dictSeatClass = dictSeatClass;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public Seat() {
    }

    @Override
    public String toString() {
        return rowNumber + "-" + columnNumber;
    }
}
