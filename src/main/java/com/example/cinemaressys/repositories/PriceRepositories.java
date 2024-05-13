package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.DictSeatClass;
import com.example.cinemaressys.entities.MovieSession;
import com.example.cinemaressys.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceRepositories extends JpaRepository<Price, Integer> {
    List<Price> findByMovieSessionMovieSessionId(int movieSessionId);
    Price findByMovieSessionAndDictSeatClass(MovieSession movieSession, DictSeatClass dictClassClass);
}
