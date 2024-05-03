package com.example.cinemaressys.controllers;

import com.example.cinemaressys.dtos.genre.GenreResponseDto;
import com.example.cinemaressys.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;
    @GetMapping("/all")
    public List<GenreResponseDto> getAll(){
        return genreService.getAllGenres();
    }
}
