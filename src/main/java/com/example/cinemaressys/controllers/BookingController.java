package com.example.cinemaressys.controllers;

import com.example.cinemaressys.dtos.booking.BookingAddBookingRequestDto;
import com.example.cinemaressys.dtos.booking.BookingResponseDto;
import com.example.cinemaressys.dtos.seat.ListSeatRequestDto;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.services.booking.BookingService;
import com.example.cinemaressys.services.seat.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }
    @PostMapping("/newBooking")
    public ResponseEntity<?> createNewBooking(@RequestBody BookingAddBookingRequestDto bookingAddBookingRequestDto) {
        try{
            BookingResponseDto bookingResponseDto = null;
            if (bookingAddBookingRequestDto.getBookingNumber() == -1) {
                bookingResponseDto = bookingService.createNewBooking(bookingAddBookingRequestDto);
            }
            else {
                bookingResponseDto = bookingService.updateBooking(bookingAddBookingRequestDto);
            }
            return ResponseEntity.ok().body(bookingResponseDto);
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/userBookings/{token}")
    public ResponseEntity<?> getUserBookings(@PathVariable String token) {
        try{
            return ResponseEntity.ok().body(bookingService.getBookingsByUserId(token));
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/userBookings/bookingDetails/{bookingId}")
    public ResponseEntity<?> getBookingsDetailsByBookingId(@PathVariable int bookingId) {
        try{
            return ResponseEntity.ok().body(bookingService.getBookingDetailsByBookingId(bookingId));
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
