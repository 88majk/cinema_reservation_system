package com.example.cinemaressys.controllers;

import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.services.booking.BookingService;
import com.example.cinemaressys.services.datagenerator.DataGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dataGenerator")
@RequiredArgsConstructor
public class DataGeneratorController {
    private final DataGeneratorService dataGeneratorService;

    @GetMapping("/generateData")
    public ResponseEntity<?> getUserBookings() {
        try{
            dataGeneratorService.generateDataAll();
            return ResponseEntity.ok().body("Dane wygenerowane prawidłowo");
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
