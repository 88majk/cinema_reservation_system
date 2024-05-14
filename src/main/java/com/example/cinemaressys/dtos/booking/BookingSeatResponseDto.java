package com.example.cinemaressys.dtos.booking;

import com.example.cinemaressys.entities.Booking;
import com.example.cinemaressys.entities.DictBookingStatus;
import com.example.cinemaressys.entities.MovieSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookingSeatResponseDto {

    private Booking booking;
    private MovieSession movieSession;
    private DictBookingStatus status;
}
