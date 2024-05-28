package com.example.cinemaressys.dtos.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookingChangeStatusRequestDto {
    private String newStatus;
}
