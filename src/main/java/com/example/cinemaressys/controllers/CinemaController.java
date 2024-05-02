package com.example.cinemaressys.controllers;

import com.example.cinemaressys.dtos.cinema.CinemaRequestDto;
import com.example.cinemaressys.dtos.cinema.CinemaResponseDto;
import com.example.cinemaressys.entities.Cinema;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.services.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema")
public class CinemaController {
    private final CinemaService cinemaService;
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CinemaRequestDto requestDto) {
        try{
            cinemaService.addCinema(requestDto);
            return ResponseEntity.ok("Cinema " + requestDto.getName() + " was succesfully added.");
        } catch (MyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occured during adding new cinema." +
                    "Please try again later.");
        }
    }
    @GetMapping("/all")
    public Collection<CinemaResponseDto> getAll() {
        return cinemaService.getAllCinemas();
    }

    @GetMapping("/{id}")
    public Optional<Cinema> getById(int cinemaId){
        return cinemaService.getCinema(cinemaId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try{
            cinemaService.deleteCinema(id);
            return ResponseEntity.ok("Cinema " + id + " was succesfully deleted.");
        } catch (MyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("An unexpected error occured during deleting the cinema." +
                    "Please try again later.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id, CinemaRequestDto requestDto){
        try {
            cinemaService.updateCinema(id, requestDto);
            return ResponseEntity.ok("Cinema was succesfully updated.");
        } catch (MyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occured during updating the cinema." +
                    "Please try again later.");
        }
    }
}
