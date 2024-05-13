package com.example.cinemaressys.dtos.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookingMovieSessionDto {
    private int movieSessionId;
    private List<BookingSeatDto> bookingSeatDtoList;
}
