package com.example.cinemaressys.dtos.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookingAddBookingRequestDto {
    private String token;
    private float totalPrice;
    private int bookingStatus;
    private int bookingNumber;
    private List<BookingMovieSessionDto> bookingMovieSessionDtoList;
}
