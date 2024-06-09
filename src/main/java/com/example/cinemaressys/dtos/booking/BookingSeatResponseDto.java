package com.example.cinemaressys.dtos.booking;

import com.example.cinemaressys.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookingSeatResponseDto {
    private String seats;
    private String seatType;
    private float price;
    private String movieName;
    private String cinemaName;
    private String hallName;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private int movieSessionId;
    private int bookingNumber;
}
