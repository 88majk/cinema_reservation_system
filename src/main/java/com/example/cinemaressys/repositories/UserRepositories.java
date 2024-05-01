package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepositories extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    @Query("SELECT u.password FROM User u WHERE u.email = :email")
    String findPasswordByEmail(@Param("email") String email);
}
