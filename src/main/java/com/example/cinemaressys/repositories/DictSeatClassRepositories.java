package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.CinemaHall;
import com.example.cinemaressys.entities.DictSeatClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictSeatClassRepositories extends JpaRepository<DictSeatClass, Integer> {
    DictSeatClass findByName(String name);
    DictSeatClass findByDictSeatClassId(int dictSeatClassId);
}
