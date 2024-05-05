package com.example.cinemaressys.services.cinema;

import com.example.cinemaressys.dtos.cinema.CinemaRequestDto;
import com.example.cinemaressys.dtos.cinema.CinemaResponseDto;
import com.example.cinemaressys.entities.Cinema;
import com.example.cinemaressys.repositories.CinemaRepositories;
import com.example.cinemaressys.services.access.AccessService;
import com.example.cinemaressys.services.access.AccessServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepositories cinemaRepository;
    private final AccessServiceImpl accessServiceImpl;

    @Override
    public void addCinema(CinemaRequestDto requestDto) {
        Cinema cinema = new Cinema(requestDto.getAddress(),
                requestDto.getName(),
                requestDto.getLocalization(),
                requestDto.getPhoneNumber(),
                requestDto.getEmailContact());
        cinemaRepository.save(cinema);
        accessServiceImpl.addAdminToNewCinema(cinema);
    }

    @Override
    public List<CinemaResponseDto> getAllCinemas() {
        return cinemaRepository.findAll().stream()
                .map(cinema -> {
                    Set<String> halls = cinemaRepository.getHallsByCinemaId(cinema.getCinemaId());
                    return new CinemaResponseDto(
                            cinema.getCinemaId(), cinema.getAddress(),
                            cinema.getName(), cinema.getLocalization(),
                            cinema.getPhoneNumber(), cinema.getEmailContact(),
                            halls);
                })
                        .collect(Collectors.toList());
    }

    @Override
    public CinemaResponseDto getCinema(int id) {
        return cinemaRepository.findById(id)
                .map(cinema ->
                {
                    Set<String> halls = cinemaRepository.getHallsByCinemaId(id);
                    return new CinemaResponseDto(
                            cinema.getCinemaId(),
                            cinema.getAddress(),
                            cinema.getName(),
                            cinema.getLocalization(),
                            cinema.getPhoneNumber(),
                            cinema.getEmailContact(),
                            halls
                    );
                }
                ).orElse(null);
    }

    @Override
    public void updateCinema(int cinemaId, CinemaRequestDto requestDto) {
        Cinema existingCinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new EntityNotFoundException("Cinema with id " + cinemaId + " not found!"));

        existingCinema.setName(requestDto.getName());
        existingCinema.setAddress(requestDto.getAddress());
        existingCinema.setLocalization(requestDto.getLocalization());
        existingCinema.setPhoneNumber(requestDto.getPhoneNumber());
        existingCinema.setEmailContact(requestDto.getEmailContact());
        cinemaRepository.save(existingCinema);
    }

    @Override
    public Boolean deleteCinema(int cinemaId) {
        if(cinemaRepository.existsById(cinemaId)){
            cinemaRepository.deleteById(cinemaId);
            return true;
        } else
            return false;
    }
}
