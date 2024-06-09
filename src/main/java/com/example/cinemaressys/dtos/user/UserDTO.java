package com.example.cinemaressys.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDTO {
    String name;
    String surname;
    String email;
    LocalDate dateOfBirth;
}
