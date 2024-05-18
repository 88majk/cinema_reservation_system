package com.example.cinemaressys.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String name;
    private String surname;
    private String email;
    private LocalDate dateOfBirth;
}
