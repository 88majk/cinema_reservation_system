package com.example.cinemaressys.controllers;

import com.example.cinemaressys.dtos.access.AccessCreateAdminRequestDto;
import com.example.cinemaressys.dtos.seat.ListSeatRequestDto;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.services.access.AccessService;
import com.example.cinemaressys.services.seat.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seat")
@CrossOrigin(origins = "http://localhost:4200")
public class SeatController {
    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService){
        this.seatService = seatService;
    }
    @PostMapping("/seatsNewHall")
    public ResponseEntity<?> createSeats(@RequestBody ListSeatRequestDto listSeatRequestDto) {
        try {
            seatService.createSeatsInNewHall(listSeatRequestDto);
            return ResponseEntity.ok().body("New admin has been added!");
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
