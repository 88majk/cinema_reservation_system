package com.example.cinemaressys.dtos.seat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetSeatRequestDto {
    private int bookingId;
}
