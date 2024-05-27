package com.example.cinemaressys.services.datagenerator;

import com.example.cinemaressys.entities.CinemaHall;
import com.example.cinemaressys.entities.User;

public interface DataGeneratorService {
    void generateDataAll();
    void generateMovieSession(CinemaHall cinemaHall, User user);
}
