package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.DictBookingStatus;
import com.example.cinemaressys.entities.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictBookingStatusRepositories extends JpaRepository<DictBookingStatus, Integer> {
    DictBookingStatus findByDictBookingStatusId(int dictBookingStatusId);
}
