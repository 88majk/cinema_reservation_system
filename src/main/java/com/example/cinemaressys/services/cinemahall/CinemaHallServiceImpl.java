package com.example.cinemaressys.services.cinemahall;

import com.example.cinemaressys.dtos.cinemahall.CinemaHallRequestDto;
import com.example.cinemaressys.dtos.cinemahall.CinemaHallResponseDto;
import com.example.cinemaressys.entities.Cinema;
import com.example.cinemaressys.entities.CinemaHall;
import com.example.cinemaressys.repositories.CinemaHallRepositories;
import com.example.cinemaressys.repositories.CinemaRepositories;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CinemaHallServiceImpl implements CinemaHallService{
    private final CinemaHallRepositories cinemaHallRepository;
    private final CinemaRepositories cinemaRepository;
    @Override
    public void addHallToCinema(int cinemaId, CinemaHallRequestDto requestDto) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new EntityNotFoundException("Cinema with id " + cinemaId + " not found."));
        cinemaHallRepository.save(new CinemaHall(
                cinema,
                requestDto.getName()
                ));
    }

    @Override
    public Boolean deleteHallFromCinema(int hallId) {
        if (cinemaHallRepository.existsById(hallId)){
            cinemaHallRepository.deleteById(hallId);
            return true;
        } else
            return false;
    }

    @Override
    public Boolean deleteAllHalls(int cinemaId) {
        if(cinemaRepository.existsById(cinemaId)) {
            List<CinemaHallResponseDto> hallsById = this.getHallsFromCinema(cinemaId);
            for (CinemaHallResponseDto hall: hallsById) {
                cinemaHallRepository.deleteById(hall.getCinemaHallId());
            }
            return true;
        } else
            return false;
    }

    @Override
    public List<CinemaHallResponseDto> getHallsFromCinema(int cinemaId) {
        return cinemaHallRepository.getAllHallsByCinemaId(cinemaId).stream().map(
                c -> new CinemaHallResponseDto(
                        c.getCinemaHallId(),
                        c.getName(),
                        c.getCinema()
                )
        ).collect(Collectors.toList());
    }
}
