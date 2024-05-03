package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.Cinema;
import com.example.cinemaressys.entities.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CinemaRepositories extends JpaRepository<Cinema, Integer> {
    @Query("SELECT ch.name FROM CinemaHall ch WHERE ch.cinema.id = :cinemaId")
    Set<String> getHallsByCinemaId(@Param("cinemaId") int cinemaId);
}
