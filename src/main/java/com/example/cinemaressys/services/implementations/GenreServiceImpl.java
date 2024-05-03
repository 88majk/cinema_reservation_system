package com.example.cinemaressys.services.implementations;

import com.example.cinemaressys.dtos.genre.GenreResponseDto;
import com.example.cinemaressys.repositories.GenreRepositories;
import com.example.cinemaressys.services.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepositories genreRepository;
    @Override
    public List<GenreResponseDto> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(c -> new GenreResponseDto(c.getGenreId(), c.getName()))
                .collect(Collectors.toList());
    }
}
