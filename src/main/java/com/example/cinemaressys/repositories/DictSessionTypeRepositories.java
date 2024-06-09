package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.DictSeatClass;
import com.example.cinemaressys.entities.DictSessionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictSessionTypeRepositories extends JpaRepository<DictSessionType, Integer> {
    DictSessionType findByName(String name);
}
