package com.example.cinemaressys.repositories;

import com.example.cinemaressys.dtos.cinemahall.CinemaHallResponseDto;
import com.example.cinemaressys.entities.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CinemaHallRepositories extends JpaRepository<CinemaHall, Integer> {
    @Query("SELECT ch FROM CinemaHall ch WHERE ch.cinema.cinemaId = :cinemaId")
    List<CinemaHall> getAllHallsByCinemaId(@Param("cinemaId") int cinemaId);
}
