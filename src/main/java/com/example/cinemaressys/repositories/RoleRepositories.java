package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.Role;
import com.example.cinemaressys.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositories extends JpaRepository<Role, Integer> {
    Role findByName(String roleName);
}
