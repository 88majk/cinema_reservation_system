package com.example.cinemaressys.services.booking;

import com.example.cinemaressys.dtos.booking.*;

import java.util.List;

public interface BookingService {
    BookingResponseDto createNewBooking(BookingAddBookingRequestDto bookingAddBookingRequestDto);
    BookingResponseDto updateBooking(BookingAddBookingRequestDto bookingAddBookingRequestDto);
    List<BookingUserResponseDto> getBookingsByUserId(String token);
    List<BookingSeatResponseDto> getBookingDetailsByBookingId(int bookingId);
    void changeBookingStatus(int bookingId, String newStatus);
}
