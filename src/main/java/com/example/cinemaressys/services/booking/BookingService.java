package com.example.cinemaressys.services.booking;

import com.example.cinemaressys.dtos.booking.BookingAddBookingRequestDto;
import com.example.cinemaressys.dtos.booking.BookingMovieSessionDto;
import com.example.cinemaressys.dtos.booking.BookingResponseDto;

public interface BookingService {
    BookingResponseDto createNewBooking(BookingAddBookingRequestDto bookingAddBookingRequestDto);
}
