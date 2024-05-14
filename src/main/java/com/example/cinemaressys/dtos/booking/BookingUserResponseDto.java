package com.example.cinemaressys.dtos.booking;

import com.example.cinemaressys.entities.DictBookingStatus;
import com.example.cinemaressys.entities.MovieSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookingUserResponseDto {
    private int bookingNumber;
    private float totalPrice;
    private String cinemaName;
    private String movieName;
    private LocalDate sessionDate;
    private DictBookingStatus status;
}
