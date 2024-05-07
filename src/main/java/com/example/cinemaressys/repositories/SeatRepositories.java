package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepositories extends JpaRepository<Seat, Integer> {
    List<Seat> findByCinemaHallCinemaHallId(int cinemaHallId);
}
