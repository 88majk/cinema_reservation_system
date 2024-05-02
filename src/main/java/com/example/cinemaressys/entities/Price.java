package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priceId;

    @ManyToOne
    @JoinColumn(name = "movie_session_id")
    private MovieSession movieSession;

    @ManyToOne
    @JoinColumn(name = "dict_seat_class_id")
    private DictSeatClass dictSeatClass;

    private float price;

    public Price(MovieSession movieSession, DictSeatClass dictSeatClass, float price) {
        this.movieSession = movieSession;
        this.dictSeatClass = dictSeatClass;
        this.price = price;
    }

    public Price() {
    }
}
