package com.example.cinemaressys.dtos.booking;

import com.example.cinemaressys.entities.DictBookingStatus;
import com.example.cinemaressys.entities.MovieSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookingUserResponseDto {
    private int bookingId;
    private int bookingNumber;
    private float totalPrice;
    private String cinemaName;
    private int movieSessionId;
    private String movieName;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private DictBookingStatus status;
}
