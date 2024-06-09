package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepositories extends JpaRepository<Genre, Integer> {
    Genre findByName(String name);
}
