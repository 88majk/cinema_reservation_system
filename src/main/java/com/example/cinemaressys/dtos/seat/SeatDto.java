package com.example.cinemaressys.dtos.seat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SeatDto {
    private int seatId;
    private char row;
    private int column;
    private String seatClass;
    private boolean isAvailable;
    private float price;
    private String bookingStatus;
    private boolean isInBooking;
}
