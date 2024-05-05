package com.example.cinemaressys.controllers;

import com.example.cinemaressys.dtos.cinemahall.CinemaHallRequestDto;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.services.cinemahall.CinemaHallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/halls")
@RequiredArgsConstructor
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;
    @PostMapping("/add/{id}")
    public ResponseEntity<?> add(@PathVariable int id, @RequestBody CinemaHallRequestDto requestDto) {
        try {
            cinemaHallService.addHallToCinema(id, requestDto);
            return ResponseEntity.ok().body("Added new hall to cinema with id " + id + ".");
        } catch (MyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error: " + e.getMessage() +
                    ". Please try again later.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        try {
            if(cinemaHallService.deleteHallFromCinema(id))
                return ResponseEntity.ok("Hall with id " + id + " was successfully deleted.");
            else
                return ResponseEntity.ok().body("Hall with id " + id + " not found.");
        } catch (MyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error: " + e.getMessage() +
                    ". Please try again later.");
        }
    }

    @DeleteMapping("/deleteincinema/{id}")
    public ResponseEntity<?> deleteByCinemaId(@PathVariable int id) {
        try {
            if (cinemaHallService.deleteAllHalls(id))
                return ResponseEntity.ok("Halls from cinema with id " + id + " were successfully deleted.");
            else
                return ResponseEntity.ok().body("Halls from cinema with id " + id + " not found.");
        } catch (MyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error: " + e.getMessage() +
                    ". Please try again later.");
        }
    }

    @GetMapping("/incinema/{id}")
    public ResponseEntity<?> getAll(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(cinemaHallService.getHallsFromCinema(id));
        } catch (MyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error: " + e.getMessage() +
                    ". Please try again later.");
        }
    }
}
