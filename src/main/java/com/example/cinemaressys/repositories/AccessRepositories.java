package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.Access;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRepositories extends JpaRepository<Access, Integer> {
    boolean existsByUserUserId(int user_id);
    @Transactional
    void deleteByUserUserId(int userId);
}
