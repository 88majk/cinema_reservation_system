package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.BookingSeat;
import com.example.cinemaressys.entities.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingSeatRepositories extends JpaRepository<BookingSeat, Integer> {
    List<BookingSeat> getByMovieSessionMovieSessionId(int movieSessionId);
}
