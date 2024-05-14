package com.example.cinemaressys.services.booking;

import com.example.cinemaressys.dtos.booking.BookingAddBookingRequestDto;
import com.example.cinemaressys.dtos.booking.BookingMovieSessionDto;
import com.example.cinemaressys.dtos.booking.BookingResponseDto;
import com.example.cinemaressys.dtos.booking.BookingUserResponseDto;

import java.util.List;

public interface BookingService {
    BookingResponseDto createNewBooking(BookingAddBookingRequestDto bookingAddBookingRequestDto);
    BookingResponseDto updateBooking(BookingAddBookingRequestDto bookingAddBookingRequestDto);

    List<BookingUserResponseDto> getBookingsByUserId(String token);
}
