package com.example.cinemaressys.repositories;

import com.example.cinemaressys.entities.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Integer> {
    Optional<DeviceToken> findByDeviceToken(String token);
}
