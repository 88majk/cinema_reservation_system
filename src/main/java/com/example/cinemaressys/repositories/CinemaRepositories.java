package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepositories extends JpaRepository<Cinema, Integer> {
}
