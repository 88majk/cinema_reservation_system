package com.example.cinemaressys.services;

import com.example.cinemaressys.dtos.cinema.CinemaRequestDto;
import com.example.cinemaressys.dtos.cinema.CinemaResponseDto;
import com.example.cinemaressys.entities.Cinema;
import com.example.cinemaressys.repositories.CinemaRepositories;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CinemaServiceImpl implements CinemaService{
    private final CinemaRepositories cinemaRepo;

    @Override
    public void addCinema(CinemaRequestDto requestDto) {
        cinemaRepo.save(new Cinema(requestDto.getAddress(),
                requestDto.getName(),
                requestDto.getLocalization(),
                requestDto.getPhoneNumber(),
                requestDto.getEmailContact()));
    }

    @Override
    public List<CinemaResponseDto> getAllCinemas() {
        return cinemaRepo.findAll().stream()
                .map(c -> new CinemaResponseDto(c.getId(), c.getAddress(), c.getName(), c.getLocalization(),
                        c.getPhoneNumber(), c.getEmailContact()))
                        .collect(Collectors.toList());
    }

    @Override
    public Optional<Cinema> getCinema(int id) {
        return cinemaRepo.findById(id);
    }

    @Override
    public void updateCinema(int cinemaId, CinemaRequestDto requestDto) {
        Cinema existingCinema = cinemaRepo.findById(cinemaId)
                .orElseThrow(() -> new EntityNotFoundException("Cinema not found!"));

        existingCinema.setName(requestDto.getName());
        existingCinema.setAddress(requestDto.getAddress());
        existingCinema.setLocalization(requestDto.getLocalization());
        existingCinema.setPhoneNumber(requestDto.getPhoneNumber());
        existingCinema.setEmailContact(requestDto.getEmailContact());
        cinemaRepo.save(existingCinema);
    }

    @Override
    public void deleteCinema(int cinemaId) {
        cinemaRepo.deleteById(cinemaId);
    }
}
